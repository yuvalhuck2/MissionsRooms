package missions.room.SuggestionManagerTests;

import Data.Data;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class SuggestionManagerTestsRealRamStudentTeacher extends SuggestionManagerTestsRealRamStudentRepo {

    @Autowired
    private TeacherRepo realTeacherRepo;

    protected void initMocks(){
        super.initMocks();
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(suggestionManager,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    @Test
    void testWatchSuggestionsFindTeacherByIdThrowsExceptionTest(){
        when(mockTeacherCrudRepository.findById(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM)
                .getAlias()))
                .thenThrow(new DataAccessResourceFailureException(""));
        realTeacherRepo=new TeacherRepo(mockTeacherCrudRepository);
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(suggestionManager,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testWatchSuggestionsInvalid(OpCode.DB_Error);
    }
}
