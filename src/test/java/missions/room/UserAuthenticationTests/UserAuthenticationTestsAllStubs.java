package missions.room.UserAuthenticationTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import ExternalSystems.HashSystem;
import ExternalSystems.MailSender;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.Student;
import missions.room.Managers.UserAuthenticationManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.stereotype.Service;

import static Data.DataConstants.*;
import static Data.DataConstants.WRONG_TEACHER_NAME;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserAuthenticationTestsAllStubs {

    @InjectMocks
    protected UserAuthenticationManager userAuthenticationManager;

    @Mock
    protected UserRepo mockUserRepo;

    @Mock
    protected Ram mockRam;

    @Mock
    protected MailSender mockMailSender;

    @Mock
    private HashSystem hashSystem;

    protected DataGenerator dataGenerator;

    private AutoCloseable closeable;

    protected String validApiKey;

    private String newPassword;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        validApiKey=VALID_STUDENT_APIKEY;
        newPassword= "newPassword";
        initMocks();
    }

    private void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        when(hashSystem.encrypt(anyString()))
                .thenCallRealMethod();
        when(mockMailSender.send(anyString(),anyString()))
                .thenReturn(true);
        Student student =dataGenerator.getStudent(Data.VALID);
        initRam(student);
        initUserRepo(student);
    }

    protected void initUserRepo(Student student) {
        when(mockUserRepo.findUserForWrite(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockUserRepo.findUserForWrite(WRONG_TEACHER_NAME))
                .thenReturn(new Response<>(null, OpCode.Success));
        when(mockUserRepo.save(any()))
                .thenReturn(new Response<>(student, OpCode.Success));
    }

    protected void initRam(Student student) {
        when(mockRam.getAlias(validApiKey))
                .thenReturn(student.getAlias());
        when(mockRam.getAlias(NULL_TEACHER_KEY))
                .thenReturn(WRONG_TEACHER_NAME);
    }

    @Test
    void testChangePasswordHappyCase(){
        Response<Boolean> changePasswordResponse = userAuthenticationManager.changePassword(validApiKey,newPassword);
        assertTrue(changePasswordResponse.getValue());
        assertEquals(changePasswordResponse.getReason(),OpCode.Success);
    }

    @Test
    void testChangePasswordNotExistUser(){
        validApiKey=NULL_TEACHER_KEY;
        testInvalidChangePassword(OpCode.Not_Exist);
    }

    @Test
    void testChangePasswordNullPassword(){
        newPassword=null;
        testInvalidChangePassword(OpCode.Wrong_Password);
    }

    @Test
    void testChangePasswordEmptyPassword(){
        newPassword="";
        testInvalidChangePassword(OpCode.Wrong_Password);
    }

    @Test
    void testChangePasswordFindUserForWriteThrowsException(){
        when(mockUserRepo.findUserForWrite(anyString())).
                thenReturn(new Response<>(null,OpCode.DB_Error));
        testInvalidChangePassword(OpCode.DB_Error);
    }

    @Test
    void testInvalidChangePasswordSaveThrowsException(){
        when(mockUserRepo.save(any())).
                thenReturn(new Response<>(null,OpCode.DB_Error));
        testInvalidChangePassword(OpCode.DB_Error);
    }

    protected void testInvalidChangePassword(OpCode opCode) {
        Response<Boolean> changePasswordResponse = userAuthenticationManager.changePassword(validApiKey,newPassword);
        assertFalse(changePasswordResponse.getValue());
        assertEquals(changePasswordResponse.getReason(),opCode);
    }

    @AfterEach
    void tearDown() {
        tearDownMocks();
    }

    protected void tearDownMocks(){
        try {
            closeable.close();
        } catch (Exception e) {
            fail("close mocks when don't need to");
        }
    }
}
