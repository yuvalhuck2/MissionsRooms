package missions.room.ITManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataObjects.APIObjects.RegisterDetailsData;
import DataObjects.APIObjects.StudentData;
import DataObjects.APIObjects.TeacherData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.UserProfileData;
import ExternalSystems.HashSystem;
import missions.room.Domain.Classroom;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.IT;
import missions.room.Domain.Users.SchoolUser;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.ITManager;
import missions.room.Repo.*;
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

import static Data.DataConstants.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ITManagerTestsAllStubs {

    @InjectMocks
    protected ITManager itManager;

    @Mock
    protected Ram mockRam;

    @Mock
    protected UserRepo mockUserRepo;

    @Mock
    protected SchoolUserRepo mockSchoolUserRepo;

    @Mock
    protected ITRepo mockITRepo;

    @Mock
    protected RoomRepo roomRepo;

    @Mock
    protected ClassroomRepo mockClassroomRepo;

    protected DataGenerator dataGenerator;

    protected String ITApiKey;

    protected String userAlias;

    protected UserProfileData userProfileData;

    protected TeacherData teacherData;

    protected StudentData studentData;

    private AutoCloseable closeable;

    protected Student student;

    protected Teacher teacher;

    protected String classroomName;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        ITApiKey=IT_API_KEY;
        userProfileData = dataGenerator.getProfileData(Data.VALID);
        teacherData = dataGenerator.getTeacherData(Data.VALID);
        studentData = dataGenerator.getStudentData(Data.VALID_STUDENT);
        student = dataGenerator.getStudent(Data.VALID);
        teacher = dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD);
        userAlias = student.getAlias();
        initMocks();
    }

    private void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        IT it = (IT) dataGenerator.getUser(Data.VALID_IT);
        String itAlias = it.getAlias();

        IT it2 = (IT) dataGenerator.getUser(Data.VALID_IT2);
        String itAlias2 = it2.getAlias();

        SchoolUser schoolUser = dataGenerator.getStudent(Data.VALID);

        Classroom empty = dataGenerator.getClassroom(Data.Empty_Students);
        classroomName = empty.getClassName();

        initRam(itAlias);
        initUserRepo(it, itAlias2);
        initSchoolUserRepo(schoolUser);
        initITRepo(it, itAlias, it2);
        initClassroomRepo(empty);
        initRoomRepo();
        initHashSystem();
    }

    private void initRoomRepo() {
        when(roomRepo.findClassroomRoomByAlias(anyString()))
                .thenReturn(new Response<>(null, OpCode.Success));
        when(roomRepo.findGroupRoomByAlias(anyString()))
                .thenReturn(new Response<>(null, OpCode.Success));
        when(roomRepo.findStudentRoomByAlias(anyString()))
                .thenReturn(new Response<>(null, OpCode.Success));
    }

    protected void initClassroomRepo(Classroom empty) {
        Classroom classroom = dataGenerator.getClassroom(Data.Valid_Classroom);
        when(mockClassroomRepo.findForWrite(classroom.getClassName()))
                .thenReturn(new Response<>(classroom, OpCode.Success));
        when(mockClassroomRepo.findForWrite(classroomName))
                .thenReturn(new Response<>(empty, OpCode.Success));
        when(mockClassroomRepo.findForWrite(dataGenerator.
                getStudentData(Data.NOT_EXIST_CLASSROOM)
                .getClassroom()))
                .thenReturn(new Response<>(null, OpCode.Not_Exist_Classroom));
        when(mockClassroomRepo.save(classroom))
                .thenReturn(new Response<>(classroom, OpCode.Success));
        when(mockClassroomRepo.delete(any()))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockClassroomRepo.findClassroomByStudent(student.getAlias()))
                .thenReturn(new Response<>(classroom, OpCode.Success));

    }

    protected void initSchoolUserRepo(SchoolUser schoolUser) {
        when(mockSchoolUserRepo.findUserForWrite(schoolUser.getAlias()))
                .thenReturn(new Response<>(schoolUser,OpCode.Success));
        when(mockSchoolUserRepo.findUserForWrite(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(mockSchoolUserRepo.save(any()))
                .thenReturn(new Response<>(schoolUser,OpCode.Success));
    }

    private void initHashSystem() {
        try {
            Field hashSystem = ITManager.class.getDeclaredField("hashSystem");
            hashSystem.setAccessible(true);
            hashSystem.set(itManager, new HashSystem());

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    protected void initITRepo(IT it, String itAlias, IT it2) {
        when(mockITRepo.save(any(IT.class)))
                .thenReturn(new Response<>(it2, OpCode.Success));
        when(mockITRepo.findITById(itAlias))
                .thenReturn(new Response<>(it,OpCode.Success));
        when(mockITRepo.findITById(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
    }

    protected void initUserRepo(IT it, String itAlias2) {

        Teacher teacherWithClassroom = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);

        when(mockUserRepo.isExistsById(itAlias2))
                .thenReturn(new Response<>(false, OpCode.Success));
        when(mockUserRepo.isExistsById(it.getAlias()))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockUserRepo.isExistsById(teacherData.getAlias()))
                .thenReturn(new Response<>(false, OpCode.Success));
        when(mockUserRepo.isExistsById(studentData.getAlias()))
                .thenReturn(new Response<>(false, OpCode.Success));

        when(mockUserRepo.findUserForWrite(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockUserRepo.findUserForWrite(teacher.getAlias()))
                .thenReturn(new Response<>(teacher, OpCode.Success));
        when(mockUserRepo.findUserForWrite(teacherWithClassroom.getAlias()))
                .thenReturn(new Response<>(teacherWithClassroom, OpCode.Success));
        when(mockUserRepo.findUserForWrite(WRONG_USER_NAME))
                .thenReturn(new Response<>(null, OpCode.Success));
        when(mockUserRepo.delete(any()))
                .thenReturn(new Response<>(true, OpCode.Success));

    }

    protected void initRam(String itAlias) {
        when(mockRam.getAlias(IT_API_KEY))
                .thenReturn(itAlias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
    }

    @Test
    void addNewITHappyCase(){
        Response<Boolean> response = itManager.addNewIT(ITApiKey,dataGenerator.getRegisterDetails(Data.VALID_IT2));
        assertTrue(response.getValue());
        assertEquals(response.getReason(),OpCode.Success);
    }

    @Test
    void addNewITEmptyAlias(){
        addNewITInvalid(OpCode.Wrong_Alias,dataGenerator.getRegisterDetails(Data.EMPTY_ALIAS));
    }

    @Test
    void addNewITNullAlias(){
        addNewITInvalid(OpCode.Wrong_Alias,dataGenerator.getRegisterDetails(Data.NULL_ALIAS));
    }

    @Test
    void addNewITEmptyPassword(){
        addNewITInvalid(OpCode.Wrong_Password,dataGenerator.getRegisterDetails(Data.EMPTY_PASSWORD));
    }

    @Test
    void addNewITNullPassword(){
        addNewITInvalid(OpCode.Wrong_Password,dataGenerator.getRegisterDetails(Data.NULL_PASSWORD));
    }

    @Test
    void addNewITWrongKey(){
        ITApiKey = INVALID_KEY;
        addNewITInvalid(OpCode.Wrong_Key,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITNotExistAlias(){
        ITApiKey = NULL_USER_KEY;
        addNewITInvalid(OpCode.Not_Exist,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITITRepoFindByIdThrowsException(){
        when(mockITRepo.findITById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITUserRepoIsExistsByIdThrowsException(){
        when(mockUserRepo.isExistsById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITAlreadyExistAlias(){
        addNewITInvalid(OpCode.Already_Exist,dataGenerator.getRegisterDetails(Data.VALID_IT));
    }

    @Test
    void addNewITITRepoSaveThrowsException(){
        when(mockITRepo.save(any(IT.class)))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    protected void addNewITInvalid(OpCode opCode, RegisterDetailsData registerDetailsData){
        Response<Boolean> response = itManager.addNewIT(ITApiKey,registerDetailsData);
        assertFalse(response.getValue());
        assertEquals(response.getReason(),opCode);
    }

    @Test
    void updateUserDetailsHappyTest(){
        Response<Boolean> updateUserDetailsResponse = itManager.updateUserDetails(ITApiKey,dataGenerator.getProfileData(Data.VALID));
        assertTrue(updateUserDetailsResponse.getValue());
        assertEquals(updateUserDetailsResponse.getReason(),OpCode.Success);
    }

    @Test
    void updateUserDetailsHappyTestNullFirstName(){
        Response<Boolean> updateUserDetailsResponse = itManager.updateUserDetails(ITApiKey,dataGenerator.getProfileData(Data.NULL_NAME));
        assertTrue(updateUserDetailsResponse.getValue());
        assertEquals(updateUserDetailsResponse.getReason(),OpCode.Success);
    }

    @Test
    void updateUserDetailsHappyTestNullLastName(){
        Response<Boolean> updateUserDetailsResponse = itManager.updateUserDetails(ITApiKey,dataGenerator.getProfileData(Data.NULL_LAST_NAME));
        assertTrue(updateUserDetailsResponse.getValue());
        assertEquals(updateUserDetailsResponse.getReason(),OpCode.Success);
    }

    @Test
    void updateUserDetailsHappyTestNullFirstAndLastName(){
        userProfileData=dataGenerator.getProfileData(Data.NULL);
        updateUserDetailsInvalidTest(OpCode.Wrong_Details);
    }

    @Test
    void updateUserDetailsWrongKey(){
        ITApiKey = INVALID_KEY;
        updateUserDetailsInvalidTest(OpCode.Wrong_Key);
    }

    @Test
    void updateUserDetailsNotExistAlias(){
        ITApiKey = NULL_USER_KEY;
        updateUserDetailsInvalidTest(OpCode.Not_Exist);
    }

    @Test
    void updateUserDetailsTRepoFindByIdThrowsException(){
        when(mockITRepo.findITById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        updateUserDetailsInvalidTest(OpCode.DB_Error);
    }

    @Test
    void updateUserDetailsNotExistTargetUserAlias(){
        userProfileData= dataGenerator.getProfileData(Data.NULL_ALIAS);
        updateUserDetailsInvalidTest(OpCode.Not_Exist);
    }

    @Test
    void updateUserDetailsSchoolUserRepoFindForWriteThrowsException(){
        when(mockSchoolUserRepo.findUserForWrite(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        updateUserDetailsInvalidTest(OpCode.DB_Error);
    }

    @Test
    void updateUserDetailsSchoolUserRepoSaveThrowsException(){
        when(mockSchoolUserRepo.save(any()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        updateUserDetailsInvalidTest(OpCode.DB_Error);
    }

    protected void updateUserDetailsInvalidTest(OpCode opCode){
        Response<Boolean> updateUserDetailsResponse = itManager.updateUserDetails(ITApiKey,userProfileData);
        assertFalse(updateUserDetailsResponse.getValue());
        assertEquals(updateUserDetailsResponse.getReason(), opCode);
    }

    @Test
    void testAddTeacherHappyCase(){
        Response<Boolean> addTeacherResponse = itManager.addTeacher(ITApiKey,teacherData);
        assertTrue(addTeacherResponse.getValue());
        assertEquals(addTeacherResponse.getReason(), OpCode.Success);
    }

    @Test
    void testAddSupervisorHappyCase(){
        teacherData = dataGenerator.getTeacherData(Data.Supervisor);
        Response<Boolean> addTeacherResponse = itManager.addTeacher(ITApiKey,teacherData);
        assertTrue(addTeacherResponse.getValue());
        assertEquals(addTeacherResponse.getReason(), OpCode.Success);
    }

    @Test
    void testAddTeacherWrongKey(){
        ITApiKey = INVALID_KEY;
        testAddTeacherInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testAddTeacherNotExistAlias(){
        ITApiKey = NULL_USER_KEY;
        testAddTeacherInvalid(OpCode.Not_Exist);
    }

    @Test
    void testAddTeacherRepoFindByIdThrowsException(){
        when(mockITRepo.findITById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testAddTeacherInvalid(OpCode.DB_Error);
    }

    @Test
    void testAddTeacherNullAlias(){
        teacherData= dataGenerator.getTeacherData(Data.NULL_ALIAS);
        testAddTeacherInvalid(OpCode.Wrong_Alias);
    }

    @Test
    void testAddTeacherNullFName(){
        teacherData= dataGenerator.getTeacherData(Data.NULL_NAME);
        testAddTeacherInvalid(OpCode.Wrong_First_Name);
    }

    @Test
    void testAddTeacherNullLName(){
        teacherData= dataGenerator.getTeacherData(Data.NULL_LAST_NAME);
        testAddTeacherInvalid(OpCode.Wrong_Last_Name);
    }

    @Test
    void testAddTeacherAlreadyExistUser(){
        teacherData= dataGenerator.getTeacherData(Data.EXIST_IT);
        testAddTeacherInvalid(OpCode.Already_Exist);
    }

    @Test
    void testAddTeacherUserRepoIsExistsByIdThrowsException(){
        when(mockUserRepo.isExistsById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testAddTeacherInvalid(OpCode.DB_Error);
    }

    @Test
    void testAddTeacherSchoolRepoSaveThrowsException(){
        when(mockSchoolUserRepo.save(any()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testAddTeacherInvalid(OpCode.DB_Error);
    }


    protected void testAddTeacherInvalid(OpCode opCode){
        Response<Boolean> addTeacherResponse = itManager.addTeacher(ITApiKey,teacherData);
        assertFalse(addTeacherResponse.getValue());
        assertEquals(addTeacherResponse.getReason(), opCode);
    }

    @Test
    void testAddStudentHappyCase(){
        Response<Boolean> addStudentResponse = itManager.addStudent(ITApiKey,studentData);
        assertTrue(addStudentResponse.getValue());
        assertEquals(addStudentResponse.getReason(), OpCode.Success);
    }

    @Test
    void testAddStudentWrongKey(){
        ITApiKey = INVALID_KEY;
        testAddStudentInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testAddStudentNotExistAlias(){
        ITApiKey = NULL_USER_KEY;
        testAddStudentInvalid(OpCode.Not_Exist);
    }

    @Test
    void testAddStudentRepoFindByIdThrowsException(){
        when(mockITRepo.findITById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testAddStudentInvalid(OpCode.DB_Error);
    }

    @Test
    void testAddStudentNullAlias(){
        studentData= dataGenerator.getStudentData(Data.NULL_ALIAS);
        testAddStudentInvalid(OpCode.Wrong_Alias);
    }

    @Test
    void testAddStudentNullFName(){
        studentData= dataGenerator.getStudentData(Data.NULL_NAME);
        testAddStudentInvalid(OpCode.Wrong_First_Name);
    }

    @Test
    void testAddStudentNullLName(){
        studentData= dataGenerator.getStudentData(Data.NULL_LAST_NAME);
        testAddStudentInvalid(OpCode.Wrong_Last_Name);
    }

    @Test
    void testAddStudentNullGroup(){
        studentData= dataGenerator.getStudentData(Data.NOT_EXIST_CLASSGROUP);
        testAddStudentInvalid(OpCode.Wrong_Group);
    }

    @Test
    void testAddStudentBothGroup(){
        studentData= dataGenerator.getStudentData(Data.BOTH_GROUP);
        testAddStudentInvalid(OpCode.Wrong_Group);
    }

    @Test
    void testAddStudentNullClassroom(){
        studentData= dataGenerator.getStudentData(Data.NULL_CLASSROOM);
        testAddStudentInvalid(OpCode.Wrong_Classroom);
    }

    @Test
    void testAddStudentNotExistClassroom(){
        studentData= dataGenerator.getStudentData(Data.NOT_EXIST_CLASSROOM);
        testAddStudentInvalid(OpCode.Not_Exist_Classroom);
    }

    @Test
    void testAddStudentClassroomRepoFindForWriteClassroomThrowsException(){
        when(mockClassroomRepo.findForWrite(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testAddStudentInvalid(OpCode.DB_Error);
    }

    @Test
    void testAddStudentClassroomRepoSaveThrowsException(){
        when(mockClassroomRepo.save(any()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testAddStudentInvalid(OpCode.DB_Error);
    }

    @Test
    void testAddStudentAlreadyExistUser(){
        studentData= dataGenerator.getStudentData(Data.EXIST_IT);
        testAddStudentInvalid(OpCode.Already_Exist);
    }

    @Test
    void testAddStudentUserRepoIsExistsByIdThrowsException(){
        when(mockUserRepo.isExistsById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testAddStudentInvalid(OpCode.DB_Error);
    }

    protected void testAddStudentInvalid(OpCode opCode){
        Response<Boolean> addStudentResponse = itManager.addStudent(ITApiKey, studentData);
        assertFalse(addStudentResponse.getValue());
        assertEquals(addStudentResponse.getReason(), opCode);
    }

    @Test
    void testCloseClassroomHappyCase(){
        Response<Boolean> closeClassroomResponse = itManager.closeClassroom(ITApiKey, classroomName);
        assertTrue(closeClassroomResponse.getValue());
        assertEquals(closeClassroomResponse.getReason(), OpCode.Success);
    }

    @Test
    void testCloseClassroomWrongKey(){
        ITApiKey = INVALID_KEY;
        testCloseClassroomInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testCloseClassroomNotExistAlias(){
        ITApiKey = NULL_USER_KEY;
        testCloseClassroomInvalid(OpCode.Not_Exist);
    }

    @Test
    void testCloseClassroomITRepoFindByIdThrowsException(){
        when(mockITRepo.findITById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testCloseClassroomInvalid(OpCode.DB_Error);
    }

    @Test
    void testCloseClassroomNotExistClassroom(){
        classroomName = NOT_EXIST_CLASSROOM;
        testCloseClassroomInvalid(OpCode.Not_Exist_Classroom);
    }

    @Test
    void testCloseClassroomClassroomRepoFindForWriteClassroomThrowsException(){
        when(mockClassroomRepo.findForWrite(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testCloseClassroomInvalid(OpCode.DB_Error);
    }

    @Test
    void testCloseClassroomNotEmptyFromStudents(){
        classroomName = dataGenerator
                .getClassroom(Data.Valid_Classroom)
                .getClassName();
        testCloseClassroomInvalid(OpCode.Has_Students);
    }

    @Test
    void testCloseClassroomClassroomRepoDeleteClassroomThrowsException(){
        when(mockClassroomRepo.delete(any()))
                .thenReturn(new Response<>(false,OpCode.DB_Error));
        testCloseClassroomInvalid(OpCode.DB_Error);
    }

    protected void testCloseClassroomInvalid(OpCode opCode){
        Response<Boolean> closeClassroomResponse = itManager.closeClassroom(ITApiKey, classroomName);
        assertFalse(closeClassroomResponse.getValue());
        assertEquals(closeClassroomResponse.getReason(), opCode);
    }

    @Test
    void testDeleteUserHappyCase(){
        Response<Boolean> deleteUserResponse = itManager.deleteUser(ITApiKey, userAlias);
        assertTrue(deleteUserResponse.getValue());
        assertEquals(deleteUserResponse.getReason(), OpCode.Success);
    }

    @Test
    void testDeleteUserWrongKey(){
        ITApiKey = INVALID_KEY;
        testDeleteUserInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testDeleteUserNotExistAlias(){
        ITApiKey = NULL_USER_KEY;
        testDeleteUserInvalid(OpCode.Not_Exist);
    }

    @Test
    void testDeleteUserITRepoFindByIdThrowsException(){
        when(mockITRepo.findITById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testDeleteUserInvalid(OpCode.DB_Error);
    }

    @Test
    void testDeleteUserTeacherHasClassroom(){
        userAlias = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getAlias();
        testDeleteUserInvalid(OpCode.Teacher_Has_Classroom);
    }

    @Test
    void testDeleteUserNotExistUserAlias(){
        userAlias = WRONG_USER_NAME;
        testDeleteUserInvalid(OpCode.Not_Exist);
    }

    protected void testDeleteUserInvalid(OpCode opCode){
        Response<Boolean> deleteUserResponse = itManager.deleteUser(ITApiKey, userAlias);
        assertFalse(deleteUserResponse.getValue());
        assertEquals(deleteUserResponse.getReason(), opCode);
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
