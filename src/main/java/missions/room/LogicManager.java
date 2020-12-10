//package missions.room;
//
//import DataAPI.*;
//import Domain.Ram;
//import Utils.StringAndTime;
//import Repositories.StudentRepository;
//import Domain.Student;
//import ExternalSystems.HashSystem;
//import ExternalSystems.MailSender;
//import ExternalSystems.UniqueStringGenerator;
//import Utils.Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class LogicManager {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    private final HashSystem hashSystem;
//    private final MailSender sender;
//
//    //save verification codes and string for trace and clean old code
//    private final ConcurrentHashMap<String, PasswordCodeAndTime> aliasToCode;
//
//    //to access apikey and alias for trace and clean not connected users
//    private final Ram ram;
//
//    //tests constructor
//    public LogicManager(StudentRepository studentRepository, MailSender sender) {
//        this.studentRepository = studentRepository;
//        hashSystem = new HashSystem();
//        this.sender = sender;
//        aliasToCode=new ConcurrentHashMap<>();
//        ram=new Ram();
//    }
//
//    public LogicManager() {
//        hashSystem=new HashSystem();
//        sender=new MailSender();
//        aliasToCode=new ConcurrentHashMap<>();
//        ram=new Ram();
//    }
//
//    /**
//     * req 2.2 - register
//     * @param details - user details
//     * @return if mail with the code was sent successfully
//     */
//    public Response<Boolean> register (RegisterDetailsData details){
//        Response<Boolean> checkDetails= checkRegisterDetails(details);
//        if(!checkDetails.getValue()){
//            return checkDetails;
//        }
//        details.setPassword(hashSystem.encrypt(details.getPassword()));
//        return updateStudent(details);
//
//    }
//
//
//    private Response<Boolean> updateStudent(RegisterDetailsData details) {
//        //critical read path
//        Optional<Student> studentOption=studentRepository.findById(details.getAlias());
//        if(!studentOption.isPresent()){
//            return new Response<>(false, OpCode.Not_Exist);
//        }
//
//        Student student=studentOption.get();
//        if(student.getPassword()!=null){
//            return new Response<>(false,OpCode.Already_Exist);
//        }
//        //end of critical read path
//        if(checkStudentFromCSV(details,student)){
//            return sendMailAndSave(details, student);
//        }
//
//        return new Response<>(false,OpCode.Dont_Match_CSV);
//    }
//
//    /**
//     * send the mail and save the verification code that was sent
//     * @param details - user details
//     * @param student - the student himself
//     * @return
//     */
//    private Response<Boolean> sendMailAndSave(RegisterDetailsData details, Student student) {
//        String verificationCode=UniqueStringGenerator.getUniqueCode();
//        if(!sender.send(Utils.getMailFromAlias(details.getAlias()), verificationCode)){
//            return new Response<>(false, OpCode.Mail_Error);
//        }
//        this.aliasToCode.put(details.getAlias(),new PasswordCodeAndTime(verificationCode,details.getPassword()));
//        return new Response<>(true,OpCode.Success);
//    }
//
//    /**
//     * check if the student details equals to the student we got from the DB
//     * @param details
//     * @param student
//     * @return
//     */
//    private boolean checkStudentFromCSV(RegisterDetailsData details, Student student) {
//        return details.getPhoneNumber().equals(student.getPhoneNumber());
//    }
//
//
//    /**
//     *
//     * @param details - check that details are valid
//     * @return
//     */
//    private Response<Boolean> checkRegisterDetails(RegisterDetailsData details) {
//        if(!Utils.checkString(details.getPassword())){
//            return new Response<>(false,OpCode.Wrong_Password);
//        }
//        if(!Utils.checkString(details.getAlias())){
//            return new Response<>(false,OpCode.Wrong_Alias);
//        }
//        if(details.getUserType()== UserType.IT){
//            return new Response<>(false,OpCode.Wrong_UserType);
//        }
//
//        if(!Utils.checkPhone(details.getPhoneNumber())){
//            return new Response<>(false,OpCode.Wrong_Phone_Number);
//        }
//
//        return new Response<>(true,OpCode.Success);
//
//    }
//
//
//    /**
//     * req 2.2 - register code
//     * * @param alias - user alias
//     * @param code - user details
//     * @return if register succeeded
//     */
//    public Response<Boolean> registerCode(String alias, String code) {
//        if(!Utils.checkString(alias)){
//            return new Response<>(false,OpCode.Wrong_Alias);
//        }
//
//        if(!Utils.checkString(code)){
//            return new Response<>(false,OpCode.Wrong_Code);
//        }
//
//        PasswordCodeAndTime passwordCodeAndTime=aliasToCode.get(alias);
//        if(passwordCodeAndTime==null) {
//            return new Response<>(false, OpCode.Not_Registered);
//        }
//        if(!code.equals(passwordCodeAndTime.getCode())){
//            return new Response<>(false,OpCode.Code_Not_Match);
//        }
//
//        //critical write path
//        Optional<Student> studentOption=studentRepository.findById(alias);
//        if(!studentOption.isPresent()){
//            aliasToCode.remove(alias);
//            return new Response<>(false, OpCode.Not_Exist);
//        }
//
//        Student student=studentOption.get();
//        if(student.getPassword()!=null){
//            aliasToCode.remove(alias);
//            return new Response<>(false,OpCode.Already_Exist);
//        }
//        student.setPassword(passwordCodeAndTime.getPassword());
//        //-------------------------------------------
//        try {
//            studentRepository.save(student);
//        }
//        catch (Exception e){
//            return new Response<>(false,OpCode.DB_Error);
//        }
//        //end of critical write path
//
//        aliasToCode.remove(alias);
//        return new Response<>(true,OpCode.Success);
//
//
//    }
//}
