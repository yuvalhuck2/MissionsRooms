package missions.room.LoginManagerTests;

import CrudRepositories.*;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import ExternalSystemMocks.MailSenderTrueMock;
import ExternalSystems.MailSender;
import RepositoryMocks.UserRepository.UserRepositoryMock;
import javafx.util.Pair;
import missions.room.Domain.IT;
import missions.room.Domain.Student;
import missions.room.Domain.Teacher;
import missions.room.Managers.UserAuthenticationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

@Service
public class LoginManagerTestsAllStubs {

    @Autowired
    protected UserCrudRepository userRepository;

    @Autowired
    protected UserAuthenticationManager loginManager;

    protected DataGenerator dataGenerator;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
    }

    void setUpMocks(){
        MailSender mailSender=new MailSenderTrueMock();
        userRepository =new UserRepositoryMock(dataGenerator);
        loginManager =new UserAuthenticationManager(userRepository);
    }

    void loginSetUp(){
        setUpMocks();
        userRepository.save((Student)dataGenerator.getUser(Data.VALID_STUDENT));
        userRepository.save((Teacher)dataGenerator.getUser(Data.VALID_TEACHER));
        userRepository.save((IT)dataGenerator.getUser(Data.VALID_IT));

    }


    void loginTearDown(){
        userRepository.delete((Student)dataGenerator.getUser(Data.VALID_STUDENT));
        userRepository.delete((Teacher)dataGenerator.getUser(Data.VALID_TEACHER));
        userRepository.delete((IT)dataGenerator.getUser(Data.VALID_IT));
    }
    @AfterEach
    void tearDown(){

    }

    /**
     * req 2.3 login
     */

    @Test
    void testLoginStudentValid() {
        loginSetUp();
        testLoginStudentValidTest();
        loginTearDown();
    }

    protected void testLoginStudentValidTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.VALID_STUDENT);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNotEquals(response.getValue(),null);
        assertEquals(response.getReason(), OpCode.Student);

    }

    @Test
    void testLoginTeacherValid() {
        loginSetUp();
        testLoginTeacherValidTest();
        loginTearDown();
    }

    protected void testLoginTeacherValidTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.VALID_TEACHER);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNotEquals(response.getValue(),null);
        assertEquals(response.getReason(), OpCode.Teacher);

    }

    @Test
    void testLoginITValid() {
        loginSetUp();
        testLoginITValidTest();
        loginTearDown();
    }

    protected void testLoginITValidTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.VALID_IT);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNotEquals(response.getValue(),null);
        assertEquals(response.getReason(), OpCode.IT);

    }

    @Test
    void testLoginNullAlias() {
        loginSetUp();
        testLoginNullAliasTest();
        loginTearDown();
    }

    protected void testLoginNullAliasTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.NULL_ALIAS);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Wrong_Alias);

    }

    @Test
    void testLoginNullPassword() {
        loginSetUp();
        testLoginNullPasswordTest();
        loginTearDown();
    }

    protected void testLoginNullPasswordTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.NULL_PASSWORD);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Wrong_Password);

    }

    @Test
    void testLoginEmptyAlias() {
        loginSetUp();
        testLoginEmptyAliasTest();
        loginTearDown();
    }

    protected void testLoginEmptyAliasTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.EMPTY_ALIAS);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Wrong_Alias);

    }

    @Test
    void testLoginEmptyPassword() {
        loginSetUp();
        testLoginEmptyPasswordTest();
        loginTearDown();
    }

    protected void testLoginEmptyPasswordTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.EMPTY_PASSWORD);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Wrong_Password);

    }

    @Test
    void testLoginNotExistAlias() {
        loginSetUp();
        testLoginNotExistAliasTest();
        loginTearDown();
    }

    protected void testLoginNotExistAliasTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.NOT_EXIST_ALIAS);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Not_Exist);

    }

    @Test
    void testLoginWrongPassword() {
        loginSetUp();
        testLoginWrongPasswordTest();
        loginTearDown();
    }

    protected void testLoginWrongPasswordTest(){
        Pair<String,String> details=dataGenerator.getLoginDetails(Data.WRONG_PASSWORD);
        Response<String> response= loginManager.login(details.getKey(),details.getValue());
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Wrong_Password);

    }


}
