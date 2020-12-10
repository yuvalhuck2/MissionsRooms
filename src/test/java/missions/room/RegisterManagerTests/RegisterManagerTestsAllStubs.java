package missions.room.RegisterManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.PasswordCodeAndTime;
import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import missions.room.Managers.RegisterManager;
import ExternalSystemMocks.MailSenderFalseMock;
import ExternalSystemMocks.MailSenderTrueMock;
import ExternalSystems.MailSender;
import RepositoryMocks.StudentRepositoryExceptionSaveMock;
import RepositoryMocks.StudentRepositoryMock;
import Repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;


@Service
public class RegisterManagerTestsAllStubs {

    @Autowired
    protected StudentRepository studentRepository;

    @Autowired
    protected RegisterManager registerManager;

    protected DataGenerator dataGenerator;



    //------------------------------set up-----------------------------------------------//
    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
    }

    void setUpMocks(){
        MailSender mailSender=new MailSenderTrueMock();
        studentRepository=new StudentRepositoryMock(dataGenerator);
        registerManager =new RegisterManager(studentRepository,mailSender);
    }

    void registerSetUp(){
        setUpMocks();
        studentRepository.save(dataGenerator.getStudent(Data.VALID));
    }

    void registerCodeSetUp(){
        registerSetUp();
        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
        Response<Boolean> r= registerManager.register(validDetails);
        if(!r.getValue()){
            System.out.println(r.getReason());
            fail();
        }
        String code=null;
        try {
            Field aliasToCode = RegisterManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            code=(((ConcurrentHashMap<String, PasswordCodeAndTime>)aliasToCode.
                    get(registerManager)).get(validDetails.getAlias())).getCode();
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
        Response<Boolean> response= registerManager.register(dataGenerator.getRegisterDetails(Data.VALID));
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
        //check containing verification code
        try {
            Field aliasToCode=RegisterManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertTrue(((ConcurrentHashMap)aliasToCode.get(registerManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
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

    @Test
    void testRegisterInvalidFailMailSender(){
        registerSetUp();
        MailSender mailSender=new MailSenderFalseMock();
        studentRepository=new StudentRepositoryMock(dataGenerator);
        registerManager =new RegisterManager(studentRepository,mailSender);
        checkWrongRegister(Data.VALID,OpCode.Mail_Error);
        registerTearDown();
    }


    protected void checkWrongRegister(Data data,OpCode opCode){
        Response<Boolean> response= registerManager.register(dataGenerator.getRegisterDetails(data));
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
        //check not containing verification code
        try {
            Field aliasToCode=RegisterManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertFalse(((ConcurrentHashMap)aliasToCode.get(registerManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
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
        Response<Boolean> response= registerManager.registerCode(alias,code);
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
    void testRegisterCodeInvalidExceptionStudentRepository(){
        registerSetUp();
        //set up exception mock
        MailSender mailSender=new MailSenderTrueMock();
        registerManager =new RegisterManager(new StudentRepositoryExceptionSaveMock(dataGenerator),mailSender);

        //register the user
        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
        registerManager.register(validDetails);
        String code=null;
        try {
            Field aliasToCode = RegisterManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            code=(((ConcurrentHashMap<String, PasswordCodeAndTime>)aliasToCode.
                    get(registerManager)).get(validDetails.getAlias())).getCode();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        dataGenerator.setValidVerificationCode(code);

        Response<Boolean> response= registerManager.registerCode(validDetails.getAlias(),code);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.DB_Error);
        registerCodeTearDown();
    }



    protected void checkWrongRegisterCode(Data dataAlias,Data dataVerification,OpCode opCode){
        String alias=dataGenerator.getRegisterDetails(dataAlias).getAlias();
        String code=dataGenerator.getVerificationCode(dataVerification);
        Response<Boolean> response= registerManager.registerCode(alias,code);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
    }



    //---------------------------------------tearDown-------------------------------------//


    void registerCodeTearDown(){
        registerTearDown();
    }

    void registerTearDown(){
        studentRepository.delete(dataGenerator.getStudent(Data.VALID));
    }
    @AfterEach
    void tearDown(){

    }
}
