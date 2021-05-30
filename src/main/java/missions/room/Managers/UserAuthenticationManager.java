package missions.room.Managers;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.SchoolUserCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import CrudRepositories.UserCrudRepository;
import DataObjects.APIObjects.RegisterDetailsData;
import DataObjects.APIObjects.TeacherData;
import DataObjects.FlatDataObjects.*;
import ExternalSystems.HashSystem;
import ExternalSystems.MailSender;
import ExternalSystems.UniqueStringGenerator;
import ExternalSystems.VerificationCodeGenerator;
import Utils.Utils;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.BaseUser;
import missions.room.Domain.Users.SchoolUser;
import missions.room.Domain.Users.Teacher;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.SchoolUserRepo;
import missions.room.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserAuthenticationManager extends TeacherManager {

    @Autowired
    private SchoolUserRepo schoolUserRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    private  HashSystem hashSystem;
    private  MailSender sender;
    private  VerificationCodeGenerator verificationCodeGenerator;

    //save verification codes and string for trace and clean old code
    private final static ConcurrentHashMap<String, PasswordCodeAndTime> aliasToCode=new ConcurrentHashMap<>();

    private final static ConcurrentHashMap<String, PasswordAndTime> aliasToResetPassword=new ConcurrentHashMap<>();

    //tests constructor
    public UserAuthenticationManager(ClassroomRepository classroomRepository,TeacherCrudRepository teacherCrudRepository,SchoolUserCrudRepository userCrudRepo, MailSender sender) {
        super(teacherCrudRepository);
        this.classroomRepo=new ClassroomRepo(classroomRepository);
        this.schoolUserRepo = new SchoolUserRepo(userCrudRepo);
        hashSystem = new HashSystem();
        this.sender = sender;
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    //login test constructor
    public UserAuthenticationManager(UserCrudRepository userCrudRepo) {
        super();
        this.userRepo = new UserRepo(userCrudRepo);
        hashSystem = new HashSystem();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    public UserAuthenticationManager(TeacherCrudRepository teacherCrudRepository) {
        super(teacherCrudRepository);
        hashSystem = new HashSystem();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    public UserAuthenticationManager() {
        super();
        hashSystem=new HashSystem();
        sender=new MailSender();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    /**
     * req 2.2 - register
     * @param details - user details
     * @return if mail with the code was sent successfully
     */
    public Response<List<TeacherData>> register (RegisterDetailsData details){
        Response<List<TeacherData>> checkDetails= checkRegisterDetails(details);
        if(checkDetails.getReason()!= OpCode.Success){
            return checkDetails;
        }
        details.setPassword(hashSystem.encrypt(details.getPassword()));
        return updateStudent(details);

    }


    protected Response<List<TeacherData>> updateStudent(RegisterDetailsData details) {
        Response<SchoolUser> userResponse= schoolUserRepo.findUserForRead(details.getAlias());
        if(userResponse.getReason()!=OpCode.Success){
            return new Response<>(null,userResponse.getReason());
        }
        SchoolUser schoolUser =userResponse.getValue();
        if(schoolUser ==null){
            return new Response<>(null, OpCode.Not_Exist);
        }

        if(schoolUser.getPassword()!=null){
            return new Response<>(null,OpCode.Already_Exist);
        }
        List<TeacherData> teacherDataList=new ArrayList<>();
        OpCode userType=schoolUser.getOpcode();
        if(userType==OpCode.Student){
            Response<List<Teacher>> teacherListResponse=teacherRepo.findTeacherByStudent(details.getAlias());
            if(teacherListResponse.getReason()!=OpCode.Success){
                return new Response<>(null,teacherListResponse.getReason());
            }
            for (Teacher teacher : teacherListResponse.getValue()) {
                teacherDataList.add(teacher.getData());
            }
        }

        return sendMailAndSave(details,teacherDataList,userType);
    }

    /**
     * send the mail and save the verification code that was sent
     * @param details - user details
     * @param teacherDataList
     * @param userType
     * @return
     */
    private Response<List<TeacherData>> sendMailAndSave(RegisterDetailsData details, List<TeacherData> teacherDataList, OpCode userType) {
        String verificationCode= verificationCodeGenerator.getNext();
        if(!sender.send(Utils.getMailFromAlias(details.getAlias()), verificationCode)){
            return new Response<>(null, OpCode.Mail_Error);
        }
        aliasToCode.put(details.getAlias(),new PasswordCodeAndTime(verificationCode,details.getPassword()));
        return new Response<>(teacherDataList,userType);
    }


    /**
     *
     * @return if  details are valid
     */
    private Response<List<TeacherData>> checkRegisterDetails(RegisterDetailsData details) {
        if(!Utils.checkString(details.getPassword())){
            return new Response<>(null,OpCode.Wrong_Password);
        }
        if(!Utils.checkString(details.getAlias())){
            return new Response<>(null,OpCode.Wrong_Alias);
        }

        return new Response<>(null,OpCode.Success);

    }


    /**
     * req 2.2 - register code
     * * @param alias - user alias
     * @param code - user details
     * @param groupType - group type if it is student and c if it is a teacher
     * @return if register succeeded
     */
    public Response<Boolean> registerCode(String alias, String code, String teacherAlias, GroupType groupType) {
        if(!Utils.checkString(alias)){
            return new Response<>(false,OpCode.Wrong_Alias);
        }

        if(!Utils.checkString(code)){
            return new Response<>(false,OpCode.Wrong_Code);
        }

        PasswordCodeAndTime passwordCodeAndTime =aliasToCode.get(alias);
        if(passwordCodeAndTime ==null) {
            return new Response<>(false, OpCode.Not_Registered);
        }
        if(!code.equals(passwordCodeAndTime.getCode())){
            return new Response<>(false,OpCode.Code_Not_Match);
        }
        if(groupType==null){
            return new Response<>(false,OpCode.Wrong_Type);
        }

        return checkPasswordAndPersist(alias, passwordCodeAndTime,teacherAlias,groupType);

    }

    /**
     *
     * @param studentAlias - studentAlias of user
     * @param passwordCodeAndTime - the password and the code match for tat studentAlias
     * @param teacherAlias
     * @return if the student was saved
     */
    // annotation means that the write lock will be taken until save function
    @Transactional
    protected Response<Boolean> checkPasswordAndPersist(String studentAlias, PasswordCodeAndTime passwordCodeAndTime, String teacherAlias, GroupType groupType) {
        Response<Teacher> teacherResponse=checkTeacherForRead(teacherAlias);
        if(teacherResponse.getReason()!=OpCode.Success){
            return new Response<>(false,teacherResponse.getReason());
        }
        Teacher teacher=teacherResponse.getValue();
        SchoolUser schoolUser;
        if(groupType==GroupType.C){//if it is registration of a teacher
            schoolUser=teacher;
        }
        else{
            schoolUser=teacher.getUnregisteredStudent(studentAlias);
            if(schoolUser ==null){
                aliasToCode.remove(studentAlias);
                return new Response<>(false, OpCode.Not_Exist);
            }
        }

        if(schoolUser.getPassword()!=null){
            aliasToCode.remove(schoolUser.getAlias());
            return new Response<>(false,OpCode.Already_Exist);
        }
        schoolUser.setPassword(passwordCodeAndTime.getPassword());
        //move from group
        Response userResponse;
        if(groupType!=GroupType.C) {//student
            Response<Boolean> moveStudentResponse=teacher.moveStudentToMyGroup(schoolUser.getAlias(),groupType);
            if(moveStudentResponse.getReason()!=OpCode.Success){
                //student without password and with group
                return moveStudentResponse;
            }
            userResponse=classroomRepo.save(teacher.getClassroom());
        }
        else {
            userResponse = schoolUserRepo.save(teacher);
        }

        if(userResponse.getReason()!=OpCode.Success){
            return new Response<>(false,userResponse.getReason());
        }

        aliasToCode.remove(schoolUser.getAlias());
        return new Response<>(true,OpCode.Success);
    }

    /**
     * req 2.3 -login
     * @param alias - user alias
     * @param password - user password
     * @return API key if login succeeded
     */
    public Response<String> login (String alias, String password){
        if(!Utils.checkString(password)){
            return new Response<>(null,OpCode.Wrong_Password);
        }
        if(!Utils.checkString(alias)){
            return new Response<>(null,OpCode.Wrong_Alias);
        }
        Response<BaseUser> rsp= userRepo.findUserForRead(alias);
        if (rsp.getReason()!=OpCode.Success){
            return new Response<>(null, rsp.getReason());
        }
        BaseUser baseUser =rsp.getValue();
        if (baseUser ==null){
            return new Response<>(null, OpCode.Not_Exist);
        }
        String api= UniqueStringGenerator.getUniqueCode(alias);
        String encryptedPassword = hashSystem.encrypt(password);
        PasswordAndTime passwordAndTime = aliasToResetPassword.get(alias);
        if(encryptedPassword.equals(baseUser.getPassword())){
            return addToRamAndGetResponse(alias, baseUser.getOpcode(), api);
        }
        else if(passwordAndTime != null &&
                encryptedPassword.equals(passwordAndTime.getPassword())){
            aliasToResetPassword.remove(alias);
            return addToRamAndGetResponse(alias, baseUser.getOpcode(), api);
        }
        else{
            return new Response<>(null,OpCode.Wrong_Password);
        }
    }

    private Response<String> addToRamAndGetResponse(String alias, OpCode userRole, String api) {
        this.ram.addApi(api,alias);
        return new Response<>(api, userRole);
    }

    /**
     * req 3.3
     * @param apiKey - authentication object
     * @param newPassword - the new password to change to
     * @return if the password was changed
     */
    @Transactional
    public Response<Boolean> changePassword(String apiKey, String newPassword) {
        if(!Utils.checkString(newPassword)){
            return new Response<>(false,OpCode.Wrong_Password);
        }
        String alias = ram.getAlias(apiKey);
        Response<BaseUser> rsp= userRepo.findUserForWrite(alias);
        if (rsp.getReason()!=OpCode.Success){
            return new Response<>(false, rsp.getReason());
        }
        BaseUser baseUser =rsp.getValue();
        if(baseUser ==null){
            return new Response<>(false,OpCode.Not_Exist);
        }
        baseUser.setPassword(hashSystem.encrypt(newPassword));
        rsp=userRepo.save(baseUser);
        return new Response<>(rsp.getReason()==OpCode.Success,rsp.getReason());
    }

    public void setHashSystem(HashSystem hashSystem) {
        this.hashSystem = hashSystem;
    }

    public void setSender(MailSender sender) {
        this.sender = sender;
    }

    public void setVerificationCodeGenerator(VerificationCodeGenerator verificationCodeGenerator) {
        this.verificationCodeGenerator = verificationCodeGenerator;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public void openWebSocket(String apiKey) {
        ram.addAlias(apiKey);
    }

    public void closeWebSocket(String apiKey) {
        ram.removeApiKey(apiKey);
    }

    /**
     * req 2.4 - reset password
     * @param alias - authentication object
     * @return if an email was sent with the temp password
     */
    public Response<Boolean> resetPassword(String alias) {
        Response<Boolean> userExistResponse = userRepo.isExistsById(alias);
        if(userExistResponse.getReason() != OpCode.Success){
            return new Response<>(false, userExistResponse.getReason());
        }

        if(!userExistResponse.getValue()){
            return new Response<>(false, OpCode.Not_Exist);
        }

        String newPassword = verificationCodeGenerator.getNext()+verificationCodeGenerator.getNext();
        aliasToResetPassword.put(alias, new PasswordAndTime(hashSystem.encrypt(newPassword)));
        if(!sender.send(Utils.getMailFromAlias(alias), newPassword)){
            return new Response<>(false, OpCode.Mail_Error);
        }

        return new Response<>(true, OpCode.Success);
    }
}
