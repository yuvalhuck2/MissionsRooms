package missions.room.PointsManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.PointsData;
import DataAPI.RecordTableData;
import DataAPI.Response;
import missions.room.Domain.Classroom;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.PointsManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.StudentRepo;
import missions.room.Repo.TeacherRepo;
import missions.room.Repo.UserRepo;
import org.assertj.core.util.Sets;
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

import java.util.ArrayList;
import java.util.HashSet;

import static Data.DataConstants.*;
import static DataAPI.OpCode.DB_Error;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PointsManagerTestsAllStubs {

    protected int points;

    protected DataGenerator dataGenerator;

    protected String studentApiKey;

    protected String teacherApiKey;

    protected String supervisorApiKey;

    protected String studentAlias;

    @InjectMocks
    protected PointsManager pointsManager;

    @Mock
    protected Ram mockRam;

    @Mock
    protected UserRepo mockUserRepo;

    @Mock
    protected StudentRepo mockStudentRepo;

    @Mock
    protected TeacherRepo mockTeacherRepo;

    @Mock
    protected ClassroomRepo mockClassroomRepo;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        dataGenerator =new DataGenerator();
        studentApiKey = VALID_STUDENT_APIKEY;
        teacherApiKey = VALID_TEACHER_APIKEY;
        supervisorApiKey = VALID_SUPERVISOR_APIKEY;
        points = 3;
        initMocks();
    }

    protected void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        Student student = dataGenerator.getStudent(Data.VALID);
        studentAlias =student.getAlias();
        Teacher teacher = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        Teacher supervisor = dataGenerator.getTeacher(Data.Supervisor);
        initRam(studentAlias, teacher.getAlias(),supervisor.getAlias());
        initUserRepo(student,teacher, supervisor);
        initTeacherRepo(teacher,studentAlias,supervisor);
        initStudentRepo(student);
        initClassroomRepo();
    }

    protected void initClassroomRepo() {
        ArrayList<Classroom> classroomArrayList = new ArrayList<>();
        classroomArrayList.add(dataGenerator.getClassroom(Data.Valid_Classroom));
        classroomArrayList.add(dataGenerator.getClassroom(Data.Valid_2Students_From_Different_Groups));
        dataGenerator.getClassroom(Data.Valid_2Students_From_Different_Groups).getClassGroups().remove(dataGenerator.getClassGroup(Data.Valid_Group));
        when(mockClassroomRepo.findAll())
                .thenReturn(new Response<>(classroomArrayList,OpCode.Success));
    }

    protected void initStudentRepo(Student student) {
        when(mockStudentRepo.findStudentForWrite(student.getAlias()))
                .thenReturn(new Response<>(student,OpCode.Success));
        when(mockStudentRepo.save(any()))
                .thenReturn(new Response<>(student,OpCode.Success));
    }

    protected void initTeacherRepo(Teacher teacher, String studentAlias, Teacher supervisor) {
        when(mockTeacherRepo.findTeacherById(teacher.getAlias()))
                .thenReturn(new Response<>(teacher,OpCode.Success));
        when(mockTeacherRepo.findTeacherById(supervisor.getAlias()))
                .thenReturn(new Response<>(supervisor,OpCode.Success));
        when(mockTeacherRepo.findTeacherById(studentAlias))
                .thenReturn(new Response<>(null,OpCode.Not_Exist));
        when(mockTeacherRepo.findTeacherById(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Not_Exist));
    }

    protected void initUserRepo(Student student, Teacher teacher, Teacher supervisor) {
        when(mockUserRepo.isExistsById(student.getAlias()))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockUserRepo.isExistsById(teacher.getAlias()))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockUserRepo.isExistsById(supervisor.getAlias()))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockUserRepo.isExistsById(WRONG_USER_NAME))
                .thenReturn(new Response<>(false,OpCode.Success));
    }

    protected void initRam(String studentAlias, String teacherAlias, String supervisorAlias) {
        when(mockRam.getAlias(studentApiKey))
                .thenReturn(studentAlias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
        when(mockRam.getAlias(teacherApiKey)).
                thenReturn(teacherAlias);
        when(mockRam.getAlias(supervisorApiKey))
                .thenReturn(supervisorAlias);

        when(mockRam.getRecordTable())
                .thenReturn(null);
    }

    @Test
    void testWatchTableStudentHappyCase(){
        Response<RecordTableData> recordTableDataResponse = pointsManager.watchTable(studentApiKey);
        assertEquals(recordTableDataResponse.getReason(),OpCode.Success);
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getStudentsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Student));
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getClassroomsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Classroom));
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getGroupsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Group));
        assertFalse(recordTableDataResponse.getValue().isSupervisor());
    }

    @Test
    void testWatchTableSupervisorHappyCase(){
        Response<RecordTableData> recordTableDataResponse = pointsManager.watchTable(supervisorApiKey);
        assertEquals(recordTableDataResponse.getReason(),OpCode.Success);
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getStudentsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Student));
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getClassroomsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Classroom));
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getGroupsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Group));
        assertTrue(recordTableDataResponse.getValue().isSupervisor());
    }

    @Test
    void testWatchTableTeacherHappyCase(){
        Response<RecordTableData> recordTableDataResponse = pointsManager.watchTable(teacherApiKey);
        assertEquals(recordTableDataResponse.getReason(),OpCode.Success);
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getStudentsPointsData()),
                dataGenerator.getPointsHasSet(Data.VALID_TEACHER));
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getClassroomsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Classroom));
        assertEquals(Sets.newHashSet(recordTableDataResponse.getValue().getGroupsPointsData()),
                dataGenerator.getPointsHasSet(Data.Valid_Group));
        assertFalse(recordTableDataResponse.getValue().isSupervisor());
    }

    @Test
    void testWatchTableIsExistUserThrowsException(){
        when(mockUserRepo.isExistsById(anyString()))
                .thenReturn(new Response<>(null, DB_Error));
        testWatchTableInvalid(DB_Error);
    }

    @Test
    void testWatchTableNotExistUser(){
        studentApiKey = NULL_USER_KEY;
        testWatchTableInvalid(OpCode.Not_Exist);
    }

    void testWatchTableInvalid(OpCode opCode){
        Response<RecordTableData> recordTableDataResponse = pointsManager.watchTable(studentApiKey);
        assertEquals(recordTableDataResponse.getReason(),opCode);
        assertNull(recordTableDataResponse.getValue());
    }

    @Test
    void testReducePointsHappyCase(){
        Response<Boolean> reducePointsResponse = pointsManager.reducePoints(teacherApiKey,studentAlias,points);
        assertEquals(reducePointsResponse.getReason(),OpCode.Success);
        assertTrue(reducePointsResponse.getValue());
    }

    @Test
    void testReducePointsNonPositivePoints(){
        points = 0;
        testReducePointsInvalid(OpCode.Negative_Points);
    }

    @Test
    void testReducePointsInvalidKey(){
        teacherApiKey = INVALID_KEY;
        testReducePointsInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testReducePointsNotExistTeacher(){
        teacherApiKey = NULL_USER_KEY;
        testReducePointsInvalid(OpCode.Not_Exist);
    }

    @Test
    void testReducePointsFindTeacherByIdThrowsException(){
        when(mockTeacherRepo.findTeacherById(anyString()))
                .thenReturn(new Response<>(null, DB_Error));
        testReducePointsInvalid(DB_Error);
    }

    @Test
    void testReducePointsDontHavePermissionTeacher(){
        studentAlias = WRONG_USER_NAME;
        testReducePointsInvalid(OpCode.Dont_Have_Permission);
    }

    @Test
    void testReducePointsFindStudentForWriteThrowsException(){
        when(mockStudentRepo.findStudentForWrite(anyString()))
                .thenReturn(new Response<>(null, DB_Error));
        testReducePointsInvalid(DB_Error);
    }

    @Test
    void testReducePointsSaveStudentThrowsException(){
        when(mockStudentRepo.save(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testReducePointsInvalid(DB_Error);
    }

    void testReducePointsInvalid(OpCode opCode){
        Response<Boolean> reducePointsResponse = pointsManager.reducePoints(teacherApiKey,studentAlias,points);
        assertEquals(reducePointsResponse.getReason(),opCode);
        assertFalse(reducePointsResponse.getValue());
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
