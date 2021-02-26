package missions.room.SuggestionManagerTests;

import CrudRepositories.SuggestionCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Suggestion;
import missions.room.Managers.StudentTeacherManager;
import missions.room.Managers.SuggestionManager;
import missions.room.Repo.StudentRepo;
import missions.room.Repo.SuggestionRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SuggestionMangerTestsAllReal extends SuggestionManagerTestsRealRamStudentTeacher{

    @Autowired
    protected SuggestionRepo realSuggestionRepo;

    @Autowired
    private SuggestionCrudRepository suggestionCrudRepository;

    @Override
    protected void initMocks() {
        super.initMocks();
        try {
            Field suggestionRepo = SuggestionManager.class.getDeclaredField("suggestionRepo");
            suggestionRepo.setAccessible(true);
            suggestionRepo.set(suggestionManager,realSuggestionRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    private void setUpAddSuggestion() {
        suggestionCrudRepository.save(dataGenerator.getSuggestion(Data.VALID));
    }

    @Override
    @Test
    void testAddSuggestionHappyTest() {
        super.testAddSuggestionHappyTest();
        Suggestion expected=dataGenerator.getSuggestion(Data.VALID);
        Suggestion actual=suggestionCrudRepository.findAll()
                .iterator()
                .next();
        assertEquals(expected,actual);
    }

    @Test
    void addSuggestionSaveThrowsExceptionTest(){
        when(mockSuggestionCrudRepository.save(any()))
                .thenThrow(new DataIntegrityViolationException(""));
        realSuggestionRepo=new SuggestionRepo(mockSuggestionCrudRepository);
        try {
            Field suggestionRepo = SuggestionManager.class.getDeclaredField("suggestionRepo");
            suggestionRepo.setAccessible(true);
            suggestionRepo.set(suggestionManager,realSuggestionRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testAddSuggestionInvalid(OpCode.DB_Error);
    }

    @Override
    @Test
    void testWatchSuggestionsHappyTest() {
        setUpAddSuggestion();
        super.testWatchSuggestionsHappyTest();
    }

    @Override
    @Test
    void testWatchSuggestionsFindAllSuggestionsThrowsExceptionTest() {
        when(mockSuggestionCrudRepository.findAll())
                .thenThrow(new DataRetrievalFailureException(""));
        realSuggestionRepo=new SuggestionRepo(mockSuggestionCrudRepository);
        try {
            Field suggestionRepo = SuggestionManager.class.getDeclaredField("suggestionRepo");
            suggestionRepo.setAccessible(true);
            suggestionRepo.set(suggestionManager,realSuggestionRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testWatchSuggestionsInvalid(OpCode.DB_Error);
    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
        suggestionCrudRepository.deleteAll();
    }
}
