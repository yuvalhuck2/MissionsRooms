package missions.room.TriviaManagerTests;

import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.Ram;
import missions.room.Domain.Suggestion;
import missions.room.Domain.TriviaSubject;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.TriviaManager;
import missions.room.Repo.TeacherRepo;
import missions.room.Repo.TriviaRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import Data.DataGenerator;
import org.springframework.test.context.TestPropertySource;

import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class TriviaManagerTestsAllStubs {

    protected DataGenerator dataGenerator;

    protected String studentApiKey;

    protected String teacherApiKey;


    @InjectMocks
    protected TriviaManager triviaManager;

    @Mock
    protected Ram mockRam;

    @Mock
    protected TeacherRepo mockTeacherRepo;

    @Mock
    protected TriviaRepo mockTriviaRepo;


    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        studentApiKey = VALID_STUDENT_APIKEY;
        teacherApiKey = VALID_TEACHER_APIKEY;
        initMocks();
    }

    protected void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        Student student=dataGenerator.getStudent(Data.VALID);
        Teacher teacher=dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD);
        when(mockRam.getAlias(studentApiKey))
                .thenReturn(student.getAlias());
        when(mockRam.getAlias(teacherApiKey))
                .thenReturn(teacher.getAlias());
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_TEACHER_KEY))
                .thenReturn(WRONG_TEACHER_NAME);
        when(mockTeacherRepo.findTeacherById(eq(teacher.getAlias())))
                .thenReturn(new Response<>(teacher,OpCode.Success));
        when(mockTeacherRepo.findTeacherById(AdditionalMatchers.not(eq(teacher.getAlias()))))
                .thenReturn(new Response<>(null, OpCode.Not_Exist));
        when(mockTriviaRepo.addTriviaSubject(any()))
                .thenReturn(OpCode.Success);
        when(mockTriviaRepo.deleteTriviaSubject(any()))
                .thenReturn(OpCode.Success);
        when(mockTriviaRepo.addTriviaQuestion(any()))
                .thenReturn(OpCode.Success);
        when(mockTriviaRepo.isSubjectExist(any())).thenReturn(true);
    }

    protected Response<Boolean> addTriviaSubject(String subject){
        return triviaManager.createTriviaSubject(teacherApiKey, subject);
    }

    @Test
    public void addTriviaSubjectSuccess(){
        Response<Boolean> res = triviaManager.createTriviaSubject(teacherApiKey, dataGenerator.getTriviaSubject(Data.VALID3));
        assertTrue(res.getValue());
        assertEquals(res.getReason(), OpCode.Success);
    }

    @Test
    public void deleteTriviaSubjectFailInvalidUser(){
        addTriviaSubject(dataGenerator.getTriviaSubject(Data.VALID));
        Response<Boolean> res = triviaManager.deleteTriviaSubject(studentApiKey, dataGenerator.getTriviaSubject(Data.VALID));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.Not_Exist);
    }


    @Test
    public void deleteTriviaSubjectSuccess(){
        addTriviaSubjectSuccess();
        String triviaSubjectToDelete = dataGenerator.getTriviaSubject(Data.VALID3);
        Response<Boolean> isDeleted = triviaManager.deleteTriviaSubject(teacherApiKey, triviaSubjectToDelete);
        assertTrue(isDeleted.getValue());
        assertEquals(isDeleted.getReason(), OpCode.Success);
    }

    @Test
    public void addTriviaQuestionSuccess(){
        addTriviaSubject(dataGenerator.getTriviaSubject(Data.VALID));
        Response<Boolean> res = triviaManager.addTriviaQuestion((teacherApiKey), dataGenerator.getTriviaQuestion(Data.VALID));
        assertTrue(res.getValue());
        assertEquals(res.getReason(), OpCode.Success);
    }

    @Test
    public void addTriviaSubjectFailInvalidUser(){
        Response<Boolean> res = triviaManager.createTriviaSubject(studentApiKey, dataGenerator.getTriviaSubject(Data.VALID));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.Not_Exist);
    }

    @Test
    public void addTriviaQuestionFailInvalidUser(){
        addTriviaSubject(dataGenerator.getTriviaSubject(Data.VALID));
        Response<Boolean> res = triviaManager.addTriviaQuestion(studentApiKey, dataGenerator.getTriviaQuestion(Data.VALID));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.Not_Exist);
    }


    @Test
    public void addTriviaSubjectInvalidSubject(){
        Response<Boolean> res = triviaManager.createTriviaSubject(teacherApiKey, dataGenerator.getTriviaSubject(Data.INVALID));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.Invalid_Trivia_Subject);
    }

    @Test
    public void deleteTriviaSubjectInvalidSubject(){
        Response<Boolean> res = triviaManager.deleteTriviaSubject(teacherApiKey, dataGenerator.getTriviaSubject(Data.INVALID));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.Invalid_Trivia_Subject);
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
