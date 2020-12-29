//package missions.room.RegisterManagerTests;
//
//import Data.Data;
//import DataAPI.OpCode;
//import DataAPI.PasswordCodeGroupAndTime;
//import DataAPI.RegisterDetailsData;
//import DataAPI.Response;
//import missions.room.Domain.Student;
//import ExternalSystemMocks.MailSenderTrueMock;
//import ExternalSystems.HashSystem;
//import ExternalSystems.MailSender;
//import missions.room.Managers.UserAuthenticationManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static org.junit.Assert.*;
//
//@SpringBootTest
//@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
//public class UserAuthenticationManagerTestsRealStudentRepo extends UserAuthenticationManagerTestsAllStubs {
//
//    //clear code to alias map before starting the tests
//    @BeforeEach
//    void SetUp(){
//        super.setUp();
//        Field aliasToCode= null;
//        try {
//            aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            ((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).clear();
//            MailSender mailSender=new MailSenderTrueMock();
//            Field logicMailSender= UserAuthenticationManager.class.getDeclaredField("sender");
//            logicMailSender.setAccessible(true);
//            logicMailSender.set(userAuthenticationManager,mailSender);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//           fail();
//        }
//
//    }
//    @Override
//    void setUpMocks(){
//    }
//
//
//    @Test
//    void testRegisterInvalidNotExistInDBStudent(){
//        registerSetUp();
//        checkWrongRegister(Data.WRONG_NAME, OpCode.Not_Exist);
//        registerTearDown();
//    }
//
//    @Test
//    void testRegisterInvalidAlreadyRegisteredStudent(){
//        registerCodeSetUp();
//        RegisterDetailsData detailsData=dataGenerator.getRegisterDetails(Data.VALID);
//        userAuthenticationManager.registerCode(detailsData.getAlias(),dataGenerator.getVerificationCode(Data.VALID));
//        Response<Boolean> response= userAuthenticationManager.register(detailsData);
//        assertFalse(response.getValue());
//        assertEquals(response.getReason(), OpCode.Already_Exist);
//        registerCodeTearDown();
//    }
//
//    /**
//     * req 2.2 register code
//     */
//    //assert that the password changed
//    @Override
//    protected void registerCodeTest(){
//        super.registerCodeTest();
//        HashSystem hashSystem=new HashSystem();
//        //wrong type because valid has hashed password
//        RegisterDetailsData valid=dataGenerator.getRegisterDetails(Data.VALID2);
//        assertEquals(userRepository.findById(valid.getAlias()).get().getPassword(),
//                hashSystem.encrypt(valid.getPassword()));
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            assertTrue(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).isEmpty());
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            fail();
//        }
//
//    }
//
//    @Override
//    protected void checkWrongRegisterCode(Data dataAlias,Data dataVerification,OpCode opCode){
//        super.checkWrongRegisterCode(dataAlias,dataVerification,opCode);
//        Student valid=dataGenerator.getStudent(Data.VALID);
//        assertNull(userRepository.findById(valid.getAlias()).get().getPassword());
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            assertTrue(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).containsKey(valid.getAlias()));
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            fail();
//        }
//
//    }
//
//    @Test
//    void testRegisterCodeInvalidNotExistInDbStudent(){
//        registerCodeSetUp();
//        userRepository.deleteById(dataGenerator.getStudent(Data.VALID).getAlias());
//        //super because he has to me removed from aliasToCode
//        super.checkWrongRegisterCode(Data.VALID,Data.VALID,OpCode.Not_Exist);
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            assertTrue(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).isEmpty());
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            fail();
//        }
//        registerCodeTearDown();
//    }
//
//    @Test
//    void testRegisterCodeInvalidAlreadyExistInDbStudent(){
//        registerCodeSetUp();
//        String code=dataGenerator.getVerificationCode(Data.VALID);
//        String alias=dataGenerator.getStudent(Data.VALID).getAlias();
//        userAuthenticationManager.registerCode(alias,code);
//        //inject alias with password code
//        try {
//            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
//            aliasToCode.setAccessible(true);
//            ((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
//                    get(userAuthenticationManager)).put(alias,new PasswordCodeGroupAndTime(code,code, details.getGroupType()));
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            fail();
//        }
//        //super because he has to me removed from aliasToCode
//        super.checkWrongRegisterCode(Data.VALID,Data.VALID,OpCode.Already_Exist);
//        registerCodeTearDown();
//    }
//
//}
