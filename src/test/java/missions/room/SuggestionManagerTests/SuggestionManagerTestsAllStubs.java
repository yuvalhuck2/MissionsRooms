package missions.room.SuggestionManagerTests;

import CrudRepositories.StudentCrudRepository;
import CrudRepositories.SuggestionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.SuggestionData;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Suggestion;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.SuggestionManager;
import missions.room.Repo.StudentRepo;
import missions.room.Repo.SuggestionRepo;
import missions.room.Repo.TeacherRepo;
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

import java.util.Collections;
import java.util.List;

import static Data.Data.VALID;
import static Data.Data.VALID_WITH_CLASSROOM;
import static Data.DataConstants.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SuggestionManagerTestsAllStubs {

    protected DataGenerator dataGenerator;

    protected String studentApiKey;

    protected String teacherApiKey;

    protected String validSuggestion;

    @InjectMocks
    protected SuggestionManager suggestionManager;

    @Mock
    protected Ram mockRam;

    @Mock
    protected TeacherRepo mockTeacherRepo;

    @Mock
    protected TeacherCrudRepository mockTeacherCrudRepository;

    @Mock
    protected StudentRepo mockStudentRepo;

    @Mock
    protected StudentCrudRepository mockStudentCrudRepository;

    @Mock
    protected SuggestionRepo mockSuggestionRepo;

    @Mock
    protected SuggestionCrudRepository mockSuggestionCrudRepository;

    private AutoCloseable closeable;

    protected String suggestionId;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        studentApiKey = VALID_STUDENT_APIKEY;
        teacherApiKey =VALID_TEACHER_APIKEY;
        suggestionId=dataGenerator.getSuggestion(VALID).getId();
        validSuggestion=dataGenerator.getSuggestion(VALID)
                .getSuggestion();
        initMocks();
    }

    protected void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        Student student=dataGenerator.getStudent(VALID);
        Suggestion suggestion=dataGenerator.getSuggestion(VALID);
        Teacher teacher=dataGenerator.getTeacher(VALID_WITH_CLASSROOM);
        when(mockRam.getAlias(studentApiKey))
                .thenReturn(student.getAlias());
        when(mockRam.getAlias(teacherApiKey))
                .thenReturn(teacher.getAlias());
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_TEACHER_KEY))
                .thenReturn(WRONG_TEACHER_NAME);
        when(mockStudentRepo.findStudentById(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockTeacherRepo.findTeacherById(teacher.getAlias()))
                .thenReturn(new Response<>(teacher,OpCode.Success));
        when(mockSuggestionRepo.save(any()))
                .thenReturn(new Response<>(suggestion,OpCode.Success));
        when(mockSuggestionRepo.findAllSuggestions())
                .thenReturn(new Response<>(Collections.singletonList(dataGenerator.getSuggestion(VALID))
                ,OpCode.Success));
        when(mockSuggestionRepo.delete(anyString()))
                .thenReturn(new Response<>(true,OpCode.Success));
    }

    @Test
    void testAddSuggestionHappyTest(){
        Response<Boolean> addSuggestionResponse=suggestionManager.addSuggestion(studentApiKey,validSuggestion);
        assertTrue(addSuggestionResponse.getValue());
        assertEquals(OpCode.Success,addSuggestionResponse.getReason());
    }

    @Test
    void addSuggestionInvalidApiKeyTest(){
        studentApiKey = INVALID_KEY;
        testAddSuggestionInvalid(OpCode.Wrong_Key);
    }


    @Test
    void addSuggestionNullStudentTest(){
        studentApiKey = NULL_USER_KEY;
        when(mockStudentRepo.findStudentById(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(mockRam.getAlias(studentApiKey))
                .thenReturn(WRONG_USER_NAME);
        testAddSuggestionInvalid(OpCode.Not_Exist);
    }

    @Test
    void addSuggestionNullSuggestionTest(){
        validSuggestion=null;
        testAddSuggestionInvalid(OpCode.Wrong_Suggestion);
    }

    @Test
    void addSuggestionEmptySuggestionTest(){
        validSuggestion="";
        testAddSuggestionInvalid(OpCode.Wrong_Suggestion);
    }

    @Test
    void addSuggestionFindStudentByIdThrowsExceptionTest(){
        when(mockStudentRepo.findStudentById(dataGenerator.getStudent(VALID)
                .getAlias()))
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        testAddSuggestionInvalid(OpCode.DB_Error);
    }

    @Test
    void addSuggestionSaveThrowsExceptionTest(){
        when(mockSuggestionRepo.save(any()))
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        testAddSuggestionInvalid(OpCode.DB_Error);
    }



    protected void testAddSuggestionInvalid(OpCode opcode){
        Response<Boolean> addSuggestionResponse=suggestionManager.addSuggestion(studentApiKey,validSuggestion);
        assertFalse(addSuggestionResponse.getValue());
        assertEquals(opcode,addSuggestionResponse.getReason());
    }

    @Test
    void testWatchSuggestionsHappyTest(){
        Response<List<SuggestionData>> actual=suggestionManager.watchSuggestions(teacherApiKey);
        List<SuggestionData> expected = Collections.singletonList(dataGenerator.getSuggestion(VALID).getData());
        assertEquals(actual.getValue(),expected);
        assertEquals(actual.getReason(),OpCode.Success);
    }

    @Test
    void testWatchSuggestionsInvalidApiKeyTest(){
        teacherApiKey=INVALID_KEY;
        testWatchSuggestionsInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testWatchSuggestionsNullTeacherTest(){
        teacherApiKey=NULL_TEACHER_KEY;
        when(mockTeacherRepo.findTeacherById(WRONG_TEACHER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        testWatchSuggestionsInvalid(OpCode.Not_Exist);
    }

    @Test
    void testWatchSuggestionsFindTeacherByIdThrowsExceptionTest(){
        when(mockTeacherRepo.findTeacherById(dataGenerator.getTeacher(VALID_WITH_CLASSROOM)
                .getAlias()))
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        testWatchSuggestionsInvalid(OpCode.DB_Error);
    }

    @Test
    void testWatchSuggestionsFindAllSuggestionsThrowsExceptionTest(){
        when(mockSuggestionRepo.findAllSuggestions())
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        testWatchSuggestionsInvalid(OpCode.DB_Error);
    }


    protected void testWatchSuggestionsInvalid(OpCode opCode) {
        Response<List<SuggestionData>> actual=suggestionManager.watchSuggestions(teacherApiKey);
        assertNull(actual.getValue());
        assertEquals(opCode,actual.getReason());
    }

    @Test
    void testDeleteSuggestionsHappyTest(){
        Response<Boolean> actual=suggestionManager.deleteSuggestion(teacherApiKey,suggestionId);
        assertTrue(actual.getValue());
        assertEquals(actual.getReason(),OpCode.Success);
    }

    @Test
    void testDeleteSuggestionInvalidApiKeyTest(){
        teacherApiKey=INVALID_KEY;
        testDeleteSuggestionInvalid(OpCode.Wrong_Key);
    }

    @Test
    void testDeleteSuggestionNullTeacherTest(){
        teacherApiKey=NULL_TEACHER_KEY;
        when(mockTeacherRepo.findTeacherById(WRONG_TEACHER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        testDeleteSuggestionInvalid(OpCode.Not_Exist);
    }

    @Test
    void testDeleteSuggestionInvalidFindTeacherByIdThrowsExceptionTest(){
        when(mockTeacherRepo.findTeacherById(dataGenerator.getTeacher(VALID_WITH_CLASSROOM)
                .getAlias()))
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        testDeleteSuggestionInvalid(OpCode.DB_Error);
    }

    @Test
    void testDeleteSuggestionInvalidDeleteSuggestionThrowsExceptionTest(){
        when(mockSuggestionRepo.delete(anyString()))
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        testDeleteSuggestionInvalid(OpCode.DB_Error);
    }


    protected void testDeleteSuggestionInvalid(OpCode opCode) {
        Response<Boolean> actual=suggestionManager.deleteSuggestion(teacherApiKey,suggestionId);
        assertNull(actual.getValue());
        assertEquals(opCode,actual.getReason());
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
