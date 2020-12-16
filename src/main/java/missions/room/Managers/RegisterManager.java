package missions.room.Managers;

import DataAPI.*;
import missions.room.Domain.SchoolUser;
import ExternalSystems.HashSystem;
import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import CrudRepositories.SchoolUserCrudRepository;
import missions.room.Repo.SchoolUserRepo;
import Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegisterManager {

    @Autowired
    private SchoolUserRepo schoolUserRepo;

    private  HashSystem hashSystem;
    private  MailSender sender;
    private  VerificationCodeGenerator verificationCodeGenerator;

    //save verification codes and string for trace and clean old code
    private final ConcurrentHashMap<String, PasswordCodeAndTime> aliasToCode;

    //tests constructor
    public RegisterManager(SchoolUserCrudRepository userCrudRepo, MailSender sender) {
        this.schoolUserRepo = new SchoolUserRepo(userCrudRepo);
        hashSystem = new HashSystem();
        this.sender = sender;
        aliasToCode=new ConcurrentHashMap<>();
        verificationCodeGenerator = new VerificationCodeGenerator();
    }

    public RegisterManager() {
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
    public Response<Boolean> register (RegisterDetailsData details){
        Response<Boolean> checkDetails= checkRegisterDetails(details);
        if(!checkDetails.getValue()){
            return checkDetails;
        }
        details.setPassword(hashSystem.encrypt(details.getPassword()));
        return updateStudent(details);

    }


    private Response<Boolean> updateStudent(RegisterDetailsData details) {
        Response<SchoolUser> userResponse= schoolUserRepo.findUserForRead(details.getAlias());
        if(userResponse.getReason()!=OpCode.Success){
            return new Response<>(false,userResponse.getReason());
        }
        SchoolUser schoolUser =userResponse.getValue();
        if(schoolUser ==null){
            return new Response<>(false, OpCode.Not_Exist);
        }

        if(schoolUser.getPassword()!=null){
            return new Response<>(false,OpCode.Already_Exist);
        }
        return sendMailAndSave(details);
    }

    /**
     * send the mail and save the verification code that was sent
     * @param details - user details
     * @return
     */
    private Response<Boolean> sendMailAndSave(RegisterDetailsData details) {
        String verificationCode= verificationCodeGenerator.getNext();
        if(!sender.send(Utils.getMailFromAlias(details.getAlias()), verificationCode)){
            return new Response<>(false, OpCode.Mail_Error);
        }
        this.aliasToCode.put(details.getAlias(),new PasswordCodeAndTime(verificationCode,details.getPassword()));
        return new Response<>(true,OpCode.Success);
    }


    /**
     *
     * @return if  details are valid
     */
    private Response<Boolean> checkRegisterDetails(RegisterDetailsData details) {
        if(!Utils.checkString(details.getPassword())){
            return new Response<>(false,OpCode.Wrong_Password);
        }
        if(!Utils.checkString(details.getAlias())){
            return new Response<>(false,OpCode.Wrong_Alias);
        }

        return new Response<>(true,OpCode.Success);

    }


    /**
     * req 2.2 - register code
     * * @param alias - user alias
     * @param code - user details
     * @return if register succeeded
     */
    public Response<Boolean> registerCode(String alias, String code) {
        if(!Utils.checkString(alias)){
            return new Response<>(false,OpCode.Wrong_Alias);
        }

        if(!Utils.checkString(code)){
            return new Response<>(false,OpCode.Wrong_Code);
        }

        PasswordCodeAndTime passwordCodeAndTime=aliasToCode.get(alias);
        if(passwordCodeAndTime==null) {
            return new Response<>(false, OpCode.Not_Registered);
        }
        if(!code.equals(passwordCodeAndTime.getCode())){
            return new Response<>(false,OpCode.Code_Not_Match);
        }

        return checkPasswordAndPersist(alias, passwordCodeAndTime);

    }

    /**
     *
     * @param alias - alias of user
     * @param passwordCodeAndTime - the password and the code match for tat alias
     * @return if the student was saved
     */
    // annotation means that the write lock will be taken until save function
    @Transactional
    protected Response<Boolean> checkPasswordAndPersist(String alias, PasswordCodeAndTime passwordCodeAndTime) {
        Response<SchoolUser> userResponse= schoolUserRepo.findUserForWrite(alias);
        if(userResponse.getReason()!= OpCode.Success){
            return new Response<>(false,userResponse.getReason());
        }
        SchoolUser schoolUser =userResponse.getValue();
        if(schoolUser ==null){
            aliasToCode.remove(alias);
            return new Response<>(false, OpCode.Not_Exist);
        }

        if(schoolUser.getPassword()!=null){
            aliasToCode.remove(alias);
            return new Response<>(false,OpCode.Already_Exist);
        }
        schoolUser.setPassword(passwordCodeAndTime.getPassword());

        userResponse= schoolUserRepo.save(schoolUser);
        if(userResponse.getReason()!=OpCode.Success){
            return new Response<>(false,userResponse.getReason());
        }
        aliasToCode.remove(alias);
        return new Response<>(true,OpCode.Success);
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
}
