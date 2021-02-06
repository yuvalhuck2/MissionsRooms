package missions.room.SuggestionManagerTests;

import CrudRepositories.StudentCrudRepository;
import CrudRepositories.SuggestionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Ram;
import missions.room.Domain.Student;
import missions.room.Domain.Suggestion;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static Data.DataConstants.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SuggestionManagerTestsAllStubs {

    protected DataGenerator dataGenerator;

    protected String apiKey;

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

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        apiKey=VALID_APIKEY;
        validSuggestion=dataGenerator.getSuggestion(Data.VALID)
                .getSuggestion();
        initMocks();
    }

    protected void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        Student student=dataGenerator.getStudent(Data.VALID);
        Suggestion suggestion=dataGenerator.getSuggestion(Data.VALID);
        when(mockRam.getApi(apiKey))
                .thenReturn(student.getAlias());
        when(mockRam.getApi(INVALID_KEY))
                .thenReturn(null);
        when(mockStudentRepo.findStudentById(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockSuggestionRepo.save(any()))
                .thenReturn(new Response<>(suggestion,OpCode.Success));
    }

    @Test
    void testAddSuggestionHappyTest(){

        Response<Boolean> addSuggestionResponse=suggestionManager.addSuggestion(apiKey,validSuggestion);
        assertTrue(addSuggestionResponse.getValue());
        assertEquals(OpCode.Success,addSuggestionResponse.getReason());
    }

    @Test
    void addSuggestionInvalidApiKeyTest(){
        apiKey= INVALID_KEY;
        testAddSuggestionInvalid(OpCode.Wrong_Key);
    }


    @Test
    void addSuggestionNullStudentTest(){
        apiKey=NULL_STUDENT_KEY;
        when(mockStudentRepo.findStudentById(WRONG_STUDENT_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(mockRam.getApi(apiKey))
                .thenReturn(WRONG_STUDENT_NAME);
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
        when(mockStudentRepo.findStudentById(dataGenerator.getStudent(Data.VALID)
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
        Response<Boolean> addSuggestionResponse=suggestionManager.addSuggestion(apiKey,validSuggestion);
        assertFalse(addSuggestionResponse.getValue());
        assertEquals(opcode,addSuggestionResponse.getReason());
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
