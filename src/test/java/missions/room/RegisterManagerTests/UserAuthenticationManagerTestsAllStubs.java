//package missions.room.RegisterManagerTests;
//
//import Data.Data;
//import Data.DataGenerator;
//import DataAPI.OpCode;
//import DataAPI.PasswordCodeGroupAndTime;
//import DataAPI.RegisterDetailsData;
//import DataAPI.Response;
//import CrudRepositories.SchoolUserCrudRepository;
//import RepositoryMocks.SchoolUserRepositry.*;
//import missions.room.Managers.UserAuthenticationManager;
//import ExternalSystemMocks.MailSenderFalseMock;
//import ExternalSystemMocks.MailSenderTrueMock;
//import ExternalSystems.MailSender;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.fail;
//
//
//@Service
//public class UserAuthenticationManagerTestsAllStubs {
//
//    @Autowired
//    protected SchoolUserCrudRepository userRepository;
//
//    @Autowired
//    protected UserAuthenticationManager userAuthenticationManager;
//
//    protected DataGenerator dataGenerator;
//
//
//
//    //------------------------------set up-----------------------------------------------//
//    @BeforeEach
//    void setUp() {
//        dataGenerator=new DataGenerator();
//    }
//
//    void setUpMocks(){
//        MailSender mailSender=new MailSenderTrueMock();
//        userRepository =new SchoolUserRepositoryMock(dataGenerator);
//        userAuthenticationManager =new UserAuthenticationManager(userRepository,mailSender);
//    }
//
//    void registerSetUp(){
//        setUpMocks();
//        userRepository.save(dataGenerator.getStudent(Data.VALID));
//
//    }
//
//    void registerCodeSetUp(){
//        registerSetUp();
//        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
//        Response<Boolean> r= userAuthenticationManager.register(validDetails);
//        if(!r.getValue()){
//            System.out.println(r.getReason());
//            fail();
//        }
//        String code=null;
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
//                    get(userAuthenticationManager)).get(validDetails.getAlias())).getCode();
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            fail();
//        }
//        dataGenerator.setValidVerificationCode(code);
//    }
//
//    //------------------------------tests------------------------------------------------//
//
//    /**
//     * req 2.2 register
//     */
//
//    @Test
//    void testRegisterValid() {
//        registerSetUp();
//        testRegisterValidTest();
//        registerTearDown();
//    }
//
//    protected void testRegisterValidTest(){
//        Response<Boolean> response= userAuthenticationManager.register(dataGenerator.getRegisterDetails(Data.VALID));
//        assertTrue(response.getValue());
//        assertEquals(response.getReason(), OpCode.Success);
//        //check containing verification code
//        try {
//            Field aliasToCode= UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            assertTrue(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            fail();
//        }
//
//    }
//
//    @Test
//    void testRegisterInvalidNullPassword(){
//        registerSetUp();
//        checkWrongRegister(Data.NULL_PASSWORD,OpCode.Wrong_Password);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterInvalidEmptyPassword(){
//        registerSetUp();
//        checkWrongRegister(Data.EMPTY_PASSWORD,OpCode.Wrong_Password);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterInvalidEmptyAlias(){
//        registerSetUp();
//        checkWrongRegister(Data.EMPTY_ALIAS,OpCode.Wrong_Alias);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterInvalidNullAlias(){
//        registerSetUp();
//        checkWrongRegister(Data.NULL_ALIAS,OpCode.Wrong_Alias);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterInvalidFailMailSender(){
//        registerSetUp();
//        MailSender mailSender=new MailSenderFalseMock();
//        userRepository =new SchoolUserRepositoryMock(dataGenerator);
//        userAuthenticationManager =new UserAuthenticationManager(userRepository,mailSender);
//        checkWrongRegister(Data.VALID,OpCode.Mail_Error);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterInvalidExceptionUserRepositoryFind(){
//        registerSetUp();
//        //set up exception mock
//        MailSender mailSender=new MailSenderTrueMock();
//        userAuthenticationManager =new UserAuthenticationManager(new SchoolUserRepositoryMockExceptionFindRead(dataGenerator),mailSender);
//        checkWrongRegister(Data.VALID,OpCode.DB_Error);
//        registerCodeTearDown();
//    }
//
//    @Test
//    void testRegisterInvalidLockExceptionUserRepositoryFind(){
//        registerSetUp();
//        //set up exception mock
//        MailSender mailSender=new MailSenderTrueMock();
//        userAuthenticationManager =new UserAuthenticationManager(new SchoolUserRepositoryMockLockExceptionFindRead(dataGenerator),mailSender);
//        checkWrongRegister(Data.VALID,OpCode.TimeOut);
//        registerCodeTearDown();
//    }
//
//
//    protected void checkWrongRegister(Data data,OpCode opCode){
//        Response<Boolean> response= userAuthenticationManager.register(dataGenerator.getRegisterDetails(data));
//        assertFalse(response.getValue());
//        assertEquals(response.getReason(), opCode);
//        //check not containing verification code
//        try {
//            Field aliasToCode= UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            assertFalse(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).containsKey(dataGenerator.getStudent(Data.VALID).getAlias()));
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            fail();
//        }
//    }
//
//    /**
//     * req 2.2 register code
//     */
//    @Test
//    void testRegisterCodeValid(){
//        registerCodeSetUp();
//        registerCodeTest();
//        registerTearDown();
//    }
//
//    protected void registerCodeTest() {
//        String code=dataGenerator.getVerificationCode(Data.VALID);
//        String alias=dataGenerator.getStudent(Data.VALID).getAlias();
//        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code);
//        assertTrue(response.getValue());
//        assertEquals(response.getReason(), OpCode.Success);
//
//    }
//
//    @Test
//    void testRegisterCodeInvalidEmptyAlias(){
//        registerCodeSetUp();
//        checkWrongRegisterCode(Data.EMPTY_ALIAS,Data.VALID,OpCode.Wrong_Alias);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidNullAlias(){
//        registerCodeSetUp();
//        checkWrongRegisterCode(Data.NULL_ALIAS,Data.VALID,OpCode.Wrong_Alias);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidEmptyCode(){
//        registerCodeSetUp();
//        checkWrongRegisterCode(Data.VALID,Data.EMPTY_CODE,OpCode.Wrong_Code);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidNullCode(){
//        registerCodeSetUp();
//        checkWrongRegisterCode(Data.VALID,Data.NULL_CODE,OpCode.Wrong_Code);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidNotRegisteredUser(){
//        registerCodeSetUp();
//        checkWrongRegisterCode(Data.WRONG_NAME,Data.VALID,OpCode.Not_Registered);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidCodeNotMatch(){
//        registerCodeSetUp();
//        checkWrongRegisterCode(Data.VALID,Data.WRONG_CODE,OpCode.Code_Not_Match);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidExceptionUserRepositorySave(){
//        registerSetUp();
//        //set up exception mock
//        MailSender mailSender=new MailSenderTrueMock();
//        userAuthenticationManager =new UserAuthenticationManager(new SchoolUserRepositoryExceptionSaveMock(dataGenerator),mailSender);
//
//        //register the user
//        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
//        userAuthenticationManager.register(validDetails);
//        String code=null;
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
//                    get(userAuthenticationManager)).get(validDetails.getAlias())).getCode();
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            fail();
//        }
//        dataGenerator.setValidVerificationCode(code);
//
//        Response<Boolean> response= userAuthenticationManager.registerCode(validDetails.getAlias(),code);
//        assertFalse(response.getValue());
//        assertEquals(response.getReason(), OpCode.DB_Error);
//        registerCodeTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidExceptionUserRepositoryFind(){
//        registerSetUp();
//        //set up exception mock
//        MailSender mailSender=new MailSenderTrueMock();
//        userAuthenticationManager =new UserAuthenticationManager(new SchoolUserRepositoryMockExceptionFindWrite(dataGenerator),mailSender);
//
//        //register the user
//        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
//        userAuthenticationManager.register(validDetails);
//        String code=null;
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
//                    get(userAuthenticationManager)).get(validDetails.getAlias())).getCode();
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            fail();
//        }
//        dataGenerator.setValidVerificationCode(code);
//
//        Response<Boolean> response= userAuthenticationManager.registerCode(validDetails.getAlias(),code);
//        assertFalse(response.getValue());
//        assertEquals(response.getReason(), OpCode.DB_Error);
//        registerCodeTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidLockExceptionUserRepositoryFind(){
//        registerSetUp();
//        //set up exception mock
//        MailSender mailSender=new MailSenderTrueMock();
//        userAuthenticationManager =new UserAuthenticationManager(new SchoolUserRepositoryMockLockExceptionFindWrite(dataGenerator),mailSender);
//
//        //register the user
//        RegisterDetailsData validDetails=dataGenerator.getRegisterDetails(Data.VALID);
//        userAuthenticationManager.register(validDetails);
//        String code=null;
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            code=(((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
//                    get(userAuthenticationManager)).get(validDetails.getAlias())).getCode();
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            fail();
//        }
//        dataGenerator.setValidVerificationCode(code);
//
//        //TODO
//        Response<Boolean> response= null;//userAuthenticationManager.registerCode(validDetails.getAlias(),code);
//        assertFalse(response.getValue());
//        assertEquals(response.getReason(), OpCode.TimeOut);
//        registerCodeTearDown();
//    }
//
//
//    protected void checkWrongRegisterCode(Data dataAlias,Data dataVerification,OpCode opCode){
//        String alias=dataGenerator.getRegisterDetails(dataAlias).getAlias();
//        String code=dataGenerator.getVerificationCode(dataVerification);
//        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code);
//        assertFalse(response.getValue());
//        assertEquals(response.getReason(), opCode);
//    }
//
//
//
//    //---------------------------------------tearDown-------------------------------------//
//
//
//    void registerCodeTearDown(){
//        registerTearDown();
//    }
//
//    void registerTearDown(){
//        userRepository.delete(dataGenerator.getStudent(Data.VALID));
//    }
//    @AfterEach
//    void tearDown(){
//
//    }
//}
