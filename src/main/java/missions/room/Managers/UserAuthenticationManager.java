package missions.room.Managers;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.TeacherCrudRepository;
import CrudRepositories.UserCrudRepository;
import DataAPI.*;
import ExternalSystems.UniqueStringGenerator;
import missions.room.Communications.Publisher.SinglePublisher;
import missions.room.Domain.*;
import ExternalSystems.HashSystem;
import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import CrudRepositories.SchoolUserCrudRepository;
import missions.room.Domain.Notifications.NonPersistenceNotification;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.SchoolUserRepo;
import Utils.Utils;
import missions.room.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    private final ConcurrentHashMap<String, PasswordCodeGroupAndTime> aliasToCode;

    //tests constructor
    public UserAuthenticationManager(ClassroomRepository classroomRepository,TeacherCrudRepository teacherCrudRepository,SchoolUserCrudRepository userCrudRepo, MailSender sender) {
        super(teacherCrudRepository);
        this.classroomRepo=new ClassroomRepo(classroomRepository);
        this.schoolUserRepo = new SchoolUserRepo(userCrudRepo);
        hashSystem = new HashSystem();
        this.sender = sender;
        aliasToCode=new ConcurrentHashMap<>();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    //login test constructor
    public UserAuthenticationManager(UserCrudRepository userCrudRepo) {
        super();
        this.userRepo = new UserRepo(userCrudRepo);
        hashSystem = new HashSystem();
        aliasToCode=new ConcurrentHashMap<>();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    public UserAuthenticationManager(TeacherCrudRepository teacherCrudRepository) {
        super(teacherCrudRepository);
        hashSystem = new HashSystem();
        aliasToCode=new ConcurrentHashMap<>();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    public UserAuthenticationManager() {
        super();
        hashSystem=new HashSystem();
        sender=new MailSender();
        aliasToCode=new ConcurrentHashMap<>();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    /**
     * req 2.2 - register
     * @param details - user details
     * @return if mail with the code was sent successfully
     */
    public Response<List<TeacherData>> register (RegisterDetailsData details){
        Response<List<TeacherData>> checkDetails= checkRegisterDetails(details);
        if(checkDetails.getReason()!=OpCode.Success){
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
        this.aliasToCode.put(details.getAlias(),new PasswordCodeGroupAndTime(verificationCode,details.getPassword()));
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
    public Response<Boolean> registerCode(String alias, String code,String teacherAlias,GroupType groupType) {
        if(!Utils.checkString(alias)){
            return new Response<>(false,OpCode.Wrong_Alias);
        }

        if(!Utils.checkString(code)){
            return new Response<>(false,OpCode.Wrong_Code);
        }

        PasswordCodeGroupAndTime passwordCodeGroupAndTime =aliasToCode.get(alias);
        if(passwordCodeGroupAndTime ==null) {
            return new Response<>(false, OpCode.Not_Registered);
        }
        if(!code.equals(passwordCodeGroupAndTime.getCode())){
            return new Response<>(false,OpCode.Code_Not_Match);
        }
        if(groupType==null){
            return new Response<>(false,OpCode.Wrong_Type);
        }

        return checkPasswordAndPersist(alias, passwordCodeGroupAndTime,teacherAlias,groupType);

    }

    /**
     *
     * @param studentAlias - studentAlias of user
     * @param passwordCodeGroupAndTime - the password and the code match for tat studentAlias
     * @param teacherAlias
     * @return if the student was saved
     */
    // annotation means that the write lock will be taken until save function
    @Transactional
    protected Response<Boolean> checkPasswordAndPersist(String studentAlias, PasswordCodeGroupAndTime passwordCodeGroupAndTime, String teacherAlias,GroupType groupType) {
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
        schoolUser.setPassword(passwordCodeGroupAndTime.getPassword());
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
        Response<User> rsp= userRepo.findUserForRead(alias);
        if (rsp.getReason()!=OpCode.Success){
            return new Response<>(null, rsp.getReason());
        }
        User user =rsp.getValue();
        if (user ==null){
            return new Response<>(null, OpCode.Not_Exist);
        }
        String api= UniqueStringGenerator.getUniqueCode(alias);
        if(hashSystem.encrypt(password).equals(user.getPassword())){
            this.ram.addApi(api,alias);
            return new Response<>(api,user.getOpcode());
        }
        else{
            return new Response<>(null,OpCode.Wrong_Password);
        }
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
}
