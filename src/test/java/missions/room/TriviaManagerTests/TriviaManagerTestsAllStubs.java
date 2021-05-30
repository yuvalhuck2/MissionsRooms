package missions.room.TriviaManagerTests;

import Data.Data;
import DataObjects.APIObjects.TriviaQuestionData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import javassist.bytecode.Opcode;
import missions.room.Domain.Ram;
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

import static Data.Data.VALID_WITH_CLASSROOM;
import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class TriviaManagerTestsAllStubs {

    protected DataGenerator dataGenerator;

    protected String studentApiKey;

    protected String teacherApiKey;

    protected String questionId;


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
        TriviaQuestionData triviaQuestion = dataGenerator.getTriviaQuestion(Data.VALID);
        questionId = triviaQuestion.getId();
        initMocks(triviaQuestion);
    }

    protected void initMocks(TriviaQuestionData triviaQuestion) {
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
        when(mockTriviaRepo.addTriviaQuestion(any()))
                .thenReturn(OpCode.Success);
        when(mockTriviaRepo.isSubjectExist(any())).thenReturn(true);
        when(mockTriviaRepo.deleteTriviaQuestion(triviaQuestion.getId())).thenReturn(OpCode.Success);
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
    public void deleteTriviaQuestionHappyCase(){
        Response<Boolean> res = triviaManager.deleteTriviaQuestion(teacherApiKey, questionId);
        assertTrue(res.getValue());
        assertEquals(res.getReason(), OpCode.Success);
    }

    @Test
    void testDeleteSuggestionInvalidApiKey(){
        teacherApiKey=INVALID_KEY;
        deleteTriviaQuestionInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testDeleteSuggestionNullTeacher(){
        teacherApiKey=NULL_TEACHER_KEY;
        when(mockTeacherRepo.findTeacherById(WRONG_TEACHER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        deleteTriviaQuestionInvalid(OpCode.Not_Exist);
    }

    @Test
    void testDeleteQuestionInvalidFindTeacherByIdThrowsException(){
        when(mockTeacherRepo.findTeacherById(dataGenerator.getTeacher(VALID_WITH_CLASSROOM)
                .getAlias()))
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        deleteTriviaQuestionInvalid(OpCode.DB_Error);
    }

    @Test
    void testDeleteSuggestionInvalidDeleteQuestionByIdThrowsException(){
        when(mockTriviaRepo.deleteTriviaQuestion(anyString()))
                .thenReturn(OpCode.DB_Error);
        deleteTriviaQuestionInvalid(OpCode.DB_Error);
    }

    @Test
    void testDeleteSuggestionInvalidHasMissionWithQuestion(){
        when(mockTriviaRepo.deleteTriviaQuestion(anyString()))
                .thenReturn(OpCode.Exist_In_Mission);
        deleteTriviaQuestionInvalid(OpCode.Exist_In_Mission);
    }

    protected void deleteTriviaQuestionInvalid(OpCode opCode){
        Response<Boolean> res = triviaManager.deleteTriviaQuestion(teacherApiKey, questionId);
        assertFalse(res.getValue());
        assertEquals(res.getReason(), opCode);
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
