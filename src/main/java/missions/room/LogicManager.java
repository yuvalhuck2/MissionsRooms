package missions.room;

import DataAPI.OpCode;
import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import DataAPI.UserType;
import Domain.CodeAndTime;
import Repositories.StudentRepository;
import Domain.Student;
import ExternalSystems.HashSystem;
import ExternalSystems.MailSender;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LogicManager {

    @Autowired
    private StudentRepository studentRepository;

    private final HashSystem hashSystem;
    private final MailSender sender;
    //save verification codes and string for trace and clean old code
    private ConcurrentHashMap<String, CodeAndTime> aliasToCode;

    //tests constructor
    public LogicManager(StudentRepository studentRepository, MailSender sender) {
        this.studentRepository = studentRepository;
        this.hashSystem = new HashSystem();
        this.sender = sender;
        this.aliasToCode=new ConcurrentHashMap<>();
    }

    public LogicManager() {
        hashSystem=new HashSystem();
        sender=new MailSender();
        this.aliasToCode=new ConcurrentHashMap<>();
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
        Optional<Student> studentOption=studentRepository.findById(details.getAlias());
        if(!studentOption.isPresent()){
            return new Response<>(false, OpCode.Not_Exist);
        }

        //critical path
        Student student=studentOption.get();
        if(student.getPassword()!=null){
            return new Response<>(false,OpCode.Already_Exist);
        }
        if(checkStudentFromCSV(details,student)){
            return sendMailAndSave(details, student);
        }
        //end of critical path
        return new Response<>(false,OpCode.Dont_Match_CSV);
    }

    /**
     * send the mail ,persist the student and save the verification code that was sent
     * @param details - user details
     * @param student - the student himself
     * @return
     */
    private Response<Boolean> sendMailAndSave(RegisterDetailsData details, Student student) {
        student.setPassword(details.getPassword());
        String verificationCode=UniqueStringGenerator.getUniqueCode();
        if(!sender.send(Utils.getMailFromAlias(details.getAlias()), verificationCode)){
            return new Response<>(false, OpCode.Mail_Error);
        }
        try {
            studentRepository.save(student);
        }
        catch (Exception e){
            return new Response<>(false,OpCode.DB_Error);
        }
        this.aliasToCode.put(details.getAlias(),new CodeAndTime(verificationCode));
        return new Response<>(true,OpCode.Success);
    }

    /**
     * check if the student details equals to the student we got from the DB
     * @param details
     * @param student
     * @return
     */
    private boolean checkStudentFromCSV(RegisterDetailsData details, Student student) {
        return details.getPhoneNumber().equals(student.getPhoneNumber());
    }


    /**
     *
     * @param details - check that details are valid
     * @return
     */
    private Response<Boolean> checkRegisterDetails(RegisterDetailsData details) {
        if(!Utils.checkString(details.getPassword())){
            return new Response<>(false,OpCode.Wrong_Password);
        }
        if(!Utils.checkString(details.getAlias())){
            return new Response<>(false,OpCode.Wrong_Alias);
        }
        if(details.getUserType()== UserType.IT){
            return new Response<>(false,OpCode.Wrong_UserType);
        }

        if(!Utils.checkPhone(details.getPhoneNumber())){
            return new Response<>(false,OpCode.Wrong_Phone_Number);
        }

        return new Response<>(true,OpCode.Success);

    }


}
