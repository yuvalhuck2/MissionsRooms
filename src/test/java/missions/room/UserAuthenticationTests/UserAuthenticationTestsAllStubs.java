package missions.room.UserAuthenticationTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.*;
import ExternalSystemMocks.MailSenderFalseMock;
import ExternalSystemMocks.MailSenderTrueMock;
import ExternalSystems.HashSystem;
import ExternalSystems.MailSender;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryExemptionSaveMock;
import RepositoryMocks.SchoolUserRepositry.SchoolUserRepositoryMock;
import RepositoryMocks.SchoolUserRepositry.SchoolUserRepositoryMockExceptionFindRead;
import RepositoryMocks.SchoolUserRepositry.SchoolUserRepositoryMockLockExceptionFindRead;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionTimeoutFindByIdFor;
import javassist.bytecode.Opcode;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.UserAuthenticationManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.SchoolUserRepo;
import missions.room.Repo.TeacherRepo;
import missions.room.Repo.UserRepo;
import org.junit.Assert;
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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    protected SchoolUserRepo mockSchoolUserRepo;

    @Mock
    protected Ram mockRam;

    @Mock
    protected MailSender mockMailSender;

    @Mock
    protected TeacherRepo mockTeacherRepo;

    @Mock
    protected ClassroomRepo mockClassroomRepo;

    @Mock
    private HashSystem hashSystem;

    protected DataGenerator dataGenerator;

    private AutoCloseable closeable;

    protected String validApiKey;

    private String newPassword;

    protected String code;

    protected String teacherAlias;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        validApiKey=VALID_STUDENT_APIKEY;
        newPassword= "newPassword";
        code = dataGenerator.getVerificationCode(Data.VALID);
        teacherAlias = dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C).getAlias();
        initMocks();
    }

    protected void setUpRegisterCode() {
        RegisterDetailsData validDetails = dataGenerator.getRegisterDetails(Data.VALID);
        try {
            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            ((ConcurrentHashMap<String, PasswordCodeGroupAndTime>)aliasToCode.
                    get(userAuthenticationManager)).put(validDetails.getAlias(),
                    new PasswordCodeGroupAndTime(code,
                            new HashSystem().encrypt(validDetails.getPassword())));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    private void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        when(hashSystem.encrypt(anyString()))
                .thenCallRealMethod();
        when(mockMailSender.send(anyString(),anyString()))
                .thenReturn(true);
        Student student =dataGenerator.getStudent(Data.VALID);
        Teacher teacher = dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C);
        initRam(student);
        initUserRepo(student);
        initTeacherRepo(teacher,student.getAlias());
        initSchoolUserRepo(student);
        initClassroomRepo();
    }

    protected void initClassroomRepo() {
        when(mockClassroomRepo.save(any()))
                .thenReturn(new Response<>(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom()
                        ,OpCode.Success));
    }

    protected void initSchoolUserRepo(Student student) {
        when(mockSchoolUserRepo.findUserForRead(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockSchoolUserRepo.save(any()))
                .thenReturn(new Response<>(student,OpCode.Success));
    }

    protected void initTeacherRepo(Teacher teacher, String alias) {
        when(mockTeacherRepo.findTeacherByStudent(alias))
                .thenReturn(new Response<>(Collections.singletonList(teacher),OpCode.Success));
        when(mockTeacherRepo.findTeacherForRead(teacher.getAlias()))
                .thenReturn(new Response<>(teacher, OpCode.Success));
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
    void testRegisterHappyCase(){
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
        checkWrongRegister(Data.NULL_PASSWORD,OpCode.Wrong_Password);
    }

    @Test
    void testRegisterInvalidEmptyPassword(){
        checkWrongRegister(Data.EMPTY_PASSWORD,OpCode.Wrong_Password);
    }

    @Test
    void testRegisterInvalidEmptyAlias(){
        checkWrongRegister(Data.EMPTY_ALIAS,OpCode.Wrong_Alias);
    }

    @Test
    void testRegisterInvalidNullAlias(){
        checkWrongRegister(Data.NULL_ALIAS,OpCode.Wrong_Alias);
    }

    @Test
    void testRegisterInvalidFailMailSender(){
        when(mockMailSender.send(anyString(),anyString()))
                .thenReturn(false);
        checkWrongRegister(Data.VALID,OpCode.Mail_Error);
    }

    @Test
    void testRegisterInvalidExceptionUserRepositoryFind(){
        when(mockSchoolUserRepo.findUserForRead(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        checkWrongRegister(Data.VALID,OpCode.DB_Error);
    }

    @Test
    void testRegisterInvalidExceptionTeacherRepositoryFindTeacherByStudent(){
        when(mockTeacherRepo.findTeacherByStudent(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        checkWrongRegister(Data.VALID,OpCode.DB_Error);
    }

    @Test
    void testRegisterInvalidNotExistInDBStudent(){
        when(mockSchoolUserRepo.findUserForRead(dataGenerator.getRegisterDetails(Data.WRONG_NAME).getAlias()))
                .thenReturn(new Response<>(null,OpCode.Success));
        checkWrongRegister(Data.WRONG_NAME, OpCode.Not_Exist);
    }

    @Test
    void testRegisterInvalidAlreadyRegisteredStudent(){
        dataGenerator.getStudent(Data.VALID).setPassword("pass");
        RegisterDetailsData detailsData=dataGenerator.getRegisterDetails(Data.VALID);
        Response<List<TeacherData>> response= userAuthenticationManager.register(detailsData);
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Already_Exist);
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

    @Test
    void testRegisterCodeHappyCase() {
        setUpRegisterCode();
        String alias=dataGenerator.getStudent(Data.VALID).getAlias();
        Teacher teacher=dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,teacher.getAlias(), GroupType.A);
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
        try {
            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertTrue(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).isEmpty());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Assert.fail();
        }
    }

    @Test
    void testRegisterCodeInvalidEmptyAlias(){
        setUpRegisterCode();
        checkWrongRegisterCode(Data.EMPTY_ALIAS,Data.VALID,OpCode.Wrong_Alias);
    }

    @Test
    void testRegisterCodeInvalidNullAlias(){
        setUpRegisterCode();
        checkWrongRegisterCode(Data.NULL_ALIAS,Data.VALID,OpCode.Wrong_Alias);
    }

    @Test
    void testRegisterCodeInvalidEmptyCode(){
        setUpRegisterCode();
        checkWrongRegisterCode(Data.VALID,Data.EMPTY_CODE,OpCode.Wrong_Code);
    }

    @Test
    void testRegisterCodeInvalidNullCode(){
        setUpRegisterCode();
        checkWrongRegisterCode(Data.VALID,Data.NULL_CODE,OpCode.Wrong_Code);
    }

    @Test
    void testRegisterCodeInvalidNotRegisteredUser(){
        setUpRegisterCode();
        checkWrongRegisterCode(Data.WRONG_NAME,Data.VALID,OpCode.Not_Registered);
    }

    @Test
    void testRegisterCodeInvalidCodeNotMatch(){
        setUpRegisterCode();
        checkWrongRegisterCode(Data.VALID,Data.WRONG_CODE,OpCode.Code_Not_Match);
    }

    @Test
    void testRegisterCodeInvalidExceptionClassroomRepositorySave(){
        setUpRegisterCode();
        when(mockClassroomRepo.save(any()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        checkWrongRegisterCode(Data.VALID,Data.VALID,OpCode.DB_Error);
    }

    @Test
    void testRegisterCodeInvalidExceptionTeacherRepositoryFind(){
        setUpRegisterCode();
        when(mockTeacherRepo.findTeacherForRead(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        checkWrongRegisterCode(Data.VALID,Data.VALID,OpCode.DB_Error);
    }

    @Test
    void testRegisterCodeInvalidNotExistInDbStudent(){
        setUpRegisterCode();
        when(mockTeacherRepo.findTeacherForRead(anyString()))
                .thenReturn(new Response<>(dataGenerator.getTeacher(Data.Valid_2Students_From_Different_Groups), OpCode.Success));
        String alias=dataGenerator.getRegisterDetails(Data.VALID).getAlias();
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,teacherAlias, GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.Not_Exist);
    }

    @Test
    void testRegisterCodeInvalidAlreadyExistInDbStudent(){
        setUpRegisterCode();
        String alias=dataGenerator.getStudent(Data.VALID).getAlias();
        dataGenerator.getStudent(Data.VALID).setPassword("pass");
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,teacherAlias, GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.Already_Exist);
    }

    protected void checkWrongRegisterCode(Data dataAlias,Data dataVerification,OpCode opCode){
        String alias=dataGenerator.getRegisterDetails(dataAlias).getAlias();
        String code=dataGenerator.getVerificationCode(dataVerification);
        Student valid=dataGenerator.getStudent(Data.VALID);
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,teacherAlias, GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
        try {
            Field aliasToCode = UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            assertTrue(((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).containsKey(valid.getAlias()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Assert.fail();
        }
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
            Field aliasToCode= UserAuthenticationManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            ((ConcurrentHashMap)aliasToCode.get(userAuthenticationManager)).clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
        try {
            closeable.close();
        } catch (Exception e) {
            fail("close mocks when don't need to");
        }
    }
}
