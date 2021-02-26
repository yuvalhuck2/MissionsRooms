package missions.room.RegisterManagerTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.*;
import CrudRepositories.SchoolUserCrudRepository;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryExemptionSaveMock;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import RepositoryMocks.SchoolUserRepositry.*;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionTimeoutFindByIdFor;
import DataAPI.GroupType;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.UserAuthenticationManager;
import ExternalSystemMocks.MailSenderFalseMock;
import ExternalSystemMocks.MailSenderTrueMock;
import ExternalSystems.MailSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;


@Service
public class UserAuthenticationManagerTestsAllStubs {

    @Autowired
    protected SchoolUserCrudRepository userRepository;

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected UserAuthenticationManager userAuthenticationManager;

    @Autowired
    protected ClassroomRepository classroomRepository;

    protected DataGenerator dataGenerator;



    //------------------------------set up-----------------------------------------------//
    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
    }

    public void setUpMocks(){
        MailSender mailSender=new MailSenderTrueMock();
        classroomRepository=new ClassRoomRepositoryMock(dataGenerator);
        userRepository =new SchoolUserRepositoryMock(dataGenerator);
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        userAuthenticationManager =new UserAuthenticationManager(new ClassRoomRepositoryMock(dataGenerator),new TeacherCrudRepositoryMock(dataGenerator),new SchoolUserRepositoryMock(dataGenerator),mailSender);
    }

    void registerSetUp(){
        setUpMocks();

        userRepository.save(dataGenerator.getStudent(Data.VALID));
        classroomRepository.save(dataGenerator.getClassroom(Data.VALID_WITH_GROUP_C));
        userRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C));

    }

    void registerCodeSetUp(){
        registerSetUp();
        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
        Response<List<TeacherData>> r= userAuthenticationManager.register(validDetails);
        if(r.getValue()==null){
            System.out.println(r.getReason());
            fail();
        }
        String code=null;
        try {
            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
                    get(userAuthenticationManager)).get(validDetails.getAlias())).getCode();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        dataGenerator.setValidVerificationCode(code);
    }

    //------------------------------tests------------------------------------------------//

    /**
     * req 2.2 register
     */

    @Test
    void testRegisterValid() {
        registerSetUp();
        testRegisterValidTest();
        registerTearDown();
    }

    protected void testRegisterValidTest(){
        Response<List<TeacherData>> response= userAuthenticationManager.register(dataGenerator.getRegisterDetails(Data.VALID));
        assertEquals(response.getReason(), OpCode.Student);
        assertFalse(response.getValue().isEmpty());
        assertEquals(response.getValue().get(0).getAlias(),dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getData().getAlias());
        assertEquals(response.getValue().get(0).getAlias(),dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getData().getAlias());
        assertEquals(response.getValue().get(0).getFirstName(),dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getData().getFirstName());
        assertEquals(response.getValue().get(0).getLastName(),dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getData().getLastName());
        //check containing verification code
        try {
            Field aliasToCode= UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertTrue(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }

    }

    @Test
    void testRegisterInvalidNullPassword(){
        registerSetUp();
        checkWrongRegister(Data.NULL_PASSWORD,OpCode.Wrong_Password);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidEmptyPassword(){
        registerSetUp();
        checkWrongRegister(Data.EMPTY_PASSWORD,OpCode.Wrong_Password);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidEmptyAlias(){
        registerSetUp();
        checkWrongRegister(Data.EMPTY_ALIAS,OpCode.Wrong_Alias);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidNullAlias(){
        registerSetUp();
        checkWrongRegister(Data.NULL_ALIAS,OpCode.Wrong_Alias);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidFailMailSender(){
        registerSetUp();
        MailSender mailSender=new MailSenderFalseMock();
        userRepository =new SchoolUserRepositoryMock(dataGenerator);
        userAuthenticationManager =new UserAuthenticationManager(classroomRepository,teacherCrudRepository,userRepository,mailSender);
        checkWrongRegister(Data.VALID,OpCode.Mail_Error);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidExceptionUserRepositoryFind(){
        registerSetUp();
        //set up exception mock
        MailSender mailSender=new MailSenderTrueMock();
        userAuthenticationManager =new UserAuthenticationManager(classroomRepository,teacherCrudRepository,new SchoolUserRepositoryMockExceptionFindRead(dataGenerator),mailSender);
        checkWrongRegister(Data.VALID,OpCode.DB_Error);
        registerCodeTearDown();
    }

    @Test
    void testRegisterInvalidLockExceptionUserRepositoryFind(){
        registerSetUp();
        //set up exception mock
        MailSender mailSender=new MailSenderTrueMock();
        userAuthenticationManager =new UserAuthenticationManager(classroomRepository,teacherCrudRepository,new SchoolUserRepositoryMockLockExceptionFindRead(dataGenerator),mailSender);
        checkWrongRegister(Data.VALID,OpCode.DB_Error);
        registerCodeTearDown();
    }


    protected void checkWrongRegister(Data data,OpCode opCode){
        Response<List<TeacherData>> response= userAuthenticationManager.register(dataGenerator.getRegisterDetails(data));
        assertNull(response.getValue());
        assertEquals(response.getReason(), opCode);
        //check not containing verification code
        try {
            Field aliasToCode= UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertFalse(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * req 2.2 register code
     */
    @Test
    void testRegisterCodeValid(){
        registerCodeSetUp();
        registerCodeTest();
        registerTearDown();
    }

    protected void registerCodeTest() {
        String code=dataGenerator.getVerificationCode(Data.VALID);
        String alias=dataGenerator.getStudent(Data.VALID).getAlias();
        Teacher teacher=dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,teacher.getAlias(), GroupType.A);
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);

    }

    @Test
    void testRegisterCodeInvalidEmptyAlias(){
        registerCodeSetUp();
        checkWrongRegisterCode(Data.EMPTY_ALIAS,Data.VALID,OpCode.Wrong_Alias);
        registerTearDown();
    }

    @Test
    void testRegisterCodeInvalidNullAlias(){
        registerCodeSetUp();
        checkWrongRegisterCode(Data.NULL_ALIAS,Data.VALID,OpCode.Wrong_Alias);
        registerTearDown();
    }

    @Test
    void testRegisterCodeInvalidEmptyCode(){
        registerCodeSetUp();
        checkWrongRegisterCode(Data.VALID,Data.EMPTY_CODE,OpCode.Wrong_Code);
        registerTearDown();
    }

    @Test
    void testRegisterCodeInvalidNullCode(){
        registerCodeSetUp();
        checkWrongRegisterCode(Data.VALID,Data.NULL_CODE,OpCode.Wrong_Code);
        registerTearDown();
    }

    @Test
    void testRegisterCodeInvalidNotRegisteredUser(){
        registerCodeSetUp();
        checkWrongRegisterCode(Data.WRONG_NAME,Data.VALID,OpCode.Not_Registered);
        registerTearDown();
    }

    @Test
    void testRegisterCodeInvalidCodeNotMatch(){
        registerCodeSetUp();
        checkWrongRegisterCode(Data.VALID,Data.WRONG_CODE,OpCode.Code_Not_Match);
        registerTearDown();
    }

    @Test
    void testRegisterCodeInvalidExceptionClassroomRepositorySave(){
        registerSetUp();
        //set up exception mock
        MailSender mailSender=new MailSenderTrueMock();
        userAuthenticationManager =new UserAuthenticationManager(new ClassRoomRepositoryExemptionSaveMock(),teacherCrudRepository,userRepository,mailSender);

        //register the user
        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
        userAuthenticationManager.register(validDetails);
        String code=null;
        try {
            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
                    get(userAuthenticationManager)).get(validDetails.getAlias()))
                    .getCode();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        dataGenerator.setValidVerificationCode(code);

        Response<Boolean> response= userAuthenticationManager.registerCode(validDetails.getAlias(),code,dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getAlias(), GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.DB_Error);
        registerCodeTearDown();
    }

    @Test
    void testRegisterCodeInvalidExceptionTeacherRepositoryFind(){
        registerSetUp();
        //set up exception mock
        MailSender mailSender=new MailSenderTrueMock();
        userAuthenticationManager =new UserAuthenticationManager(classroomRepository,new TeacherCrudRepositoryMockExceptionFindById(),userRepository,mailSender);

        //register the user
        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
        userAuthenticationManager.register(validDetails);
        String code=null;
        try {
            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
                    get(userAuthenticationManager)).get(validDetails.getAlias())).getCode();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        dataGenerator.setValidVerificationCode(code);
        Response<Boolean> response= userAuthenticationManager.registerCode(validDetails.getAlias(),code,dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getAlias(), GroupType.C);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.DB_Error);
        registerCodeTearDown();
    }

    @Test
    void testRegisterCodeInvalidLockExceptionTeacherRepositoryFind(){
        registerSetUp();
        //set up exception mock
        MailSender mailSender=new MailSenderTrueMock();
        //SchoolUser d=userRepository.findUserForRead(dataGenerator.getStudent(Data.VALID).getAlias());
        //teacherCrudRepository=new TeacherCrudRepositoryMockExceptionTimeoutFindByIdFor();
        userAuthenticationManager =new UserAuthenticationManager(classroomRepository,new TeacherCrudRepositoryMockExceptionTimeoutFindByIdFor()/*new TeacherCrudRepositoryMockExceptionTimeoutFindByIdFor()*/,userRepository,mailSender);
        //register the user
        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
        userAuthenticationManager.register(validDetails);
        String code=null;
        try {
            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
                    get(userAuthenticationManager)).get(validDetails.getAlias())).getCode();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        dataGenerator.setValidVerificationCode(code);

        Response<Boolean> response= userAuthenticationManager.registerCode(validDetails.getAlias(),code,dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getAlias(), GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.TimeOut);
        registerCodeTearDown();
    }


    protected void checkWrongRegisterCode(Data dataAlias,Data dataVerification,OpCode opCode){
        String alias=dataGenerator.getRegisterDetails(dataAlias).getAlias();
        String code=dataGenerator.getVerificationCode(dataVerification);
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getAlias(), GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
    }



    //---------------------------------------tearDown-------------------------------------//


    void registerCodeTearDown(){
        registerTearDown();
    }

    void registerTearDown(){
        userRepository.delete(dataGenerator.getStudent(Data.VALID));
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C));
        classroomRepository.delete(dataGenerator.getClassroom(Data.Valid_Classroom));

    }
    @AfterEach
    void tearDown(){


    }
}
