package missions.room.LogicManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import ExternalSystemMocks.MailSenderFalseMock;
import ExternalSystemMocks.MailSenderTrueMock;
import ExternalSystems.MailSender;
import RepositoryMocks.StudentRepositoryExceptionSaveMock;
import RepositoryMocks.StudentRepositoryMock;
import missions.room.LogicManager;
import Domain.Repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;


@Service
public class LogicManagerTestsAllStubs {

    @Autowired
    protected StudentRepository studentRepository;

    @Autowired
    protected LogicManager logicManager;

    protected DataGenerator dataGenerator;



    //------------------------------set up-----------------------------------------------//
    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
    }

    void setUpMocks(){
        MailSender mailSender=new MailSenderTrueMock();
        studentRepository=new StudentRepositoryMock(dataGenerator);
        logicManager=new LogicManager(studentRepository,mailSender);
    }

    void registerSetUp(){
        setUpMocks();
        studentRepository.save(dataGenerator.getStudent(Data.VALID));
    }

    void loginSetUp(){
        registerSetUp();
        logicManager.register(dataGenerator.getRegisterDetails(Data.VALID));
    }

    //------------------------------tests------------------------------------------------//



    @Test
    void testRegisterValid() {
        registerSetUp();
        testRegisterValidTest();
        registerTearDown();
    }

    protected void testRegisterValidTest(){
        Response<Boolean> response=logicManager.register(dataGenerator.getRegisterDetails(Data.VALID));
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
        //check containing verification code
        try {
            Field aliasToCode=LogicManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertTrue(((ConcurrentHashMap)aliasToCode.get(logicManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
        assertEquals(dataGenerator.getRegisterDetails(Data.VALID).getPassword(),
                studentRepository.findById(dataGenerator.getStudent(Data.VALID).getAlias()).get().getPassword());
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
    void testRegisterInvalidInvalidPhoneNumber(){
        registerSetUp();
        checkWrongRegister(Data.INVALID_PHONE,OpCode.Wrong_Phone_Number);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidNullPhoneNumber(){
        registerSetUp();
        checkWrongRegister(Data.NULL_PHONE,OpCode.Wrong_Phone_Number);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidUserType(){
        registerSetUp();
        checkWrongRegister(Data.WRONG_TYPE,OpCode.Wrong_UserType);
        registerTearDown();
    }

    //test that if the phone gave by the user not identical to the phone on the db
    @Test
    void testRegisterInvalidWrongPhoneNumber(){
        registerSetUp();
        checkWrongRegister(Data.WRONG_PHONE,OpCode.Dont_Match_CSV);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidFailMailSender(){
        registerSetUp();
        MailSender mailSender=new MailSenderFalseMock();
        studentRepository=new StudentRepositoryMock(dataGenerator);
        logicManager=new LogicManager(studentRepository,mailSender);
        checkWrongRegister(Data.VALID,OpCode.Mail_Error);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidExceptionStudentRepository(){
        registerSetUp();
        MailSender mailSender=new MailSenderTrueMock();
        studentRepository=new StudentRepositoryExceptionSaveMock(dataGenerator);
        logicManager=new LogicManager(studentRepository,mailSender);
        checkWrongRegister(Data.VALID,OpCode.DB_Error);
        registerTearDown();
    }

    protected void checkWrongRegister(Data data,OpCode opCode){
        Response<Boolean> response=logicManager.register(dataGenerator.getRegisterDetails(data));
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
        //check not containing verification code
        try {
            Field aliasToCode=LogicManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertFalse(((ConcurrentHashMap)aliasToCode.get(logicManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }


    //---------------------------------------tearDown-------------------------------------//


    void loginTearDown(){
        registerTearDown();
    }

    void registerTearDown(){
        studentRepository.delete(dataGenerator.getStudent(Data.VALID));
    }
    @AfterEach
    void tearDown(){

    }
}
