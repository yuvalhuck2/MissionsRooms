package missions.room.TriviaManagerTests;

import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import DataObjects.APIObjects.TriviaQuestionData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.relaxng.datatype.DatatypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.sql.SQLSyntaxErrorException;

import static Data.Data.VALID_WITH_CLASSROOM;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class TriviaManagerTestsRealRamTeacher extends  TriviaManagerTestsRealRam {

    @Autowired
    private TeacherRepo realTeacherRepo;

    @Mock
    private TeacherCrudRepository mockTeacherCrudRepository;

    @Override
    protected void initMocks(TriviaQuestionData triviaQuestion){
        super.initMocks(triviaQuestion);
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(triviaManager,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realTeacherRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
    }

    @Test
    void testDeleteQuestionInvalidFindTeacherByIdThrowsException(){
        when(mockTeacherCrudRepository.findById(any()))
                .thenThrow(new RuntimeException());
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(triviaManager,new TeacherRepo(mockTeacherCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        deleteTriviaQuestionInvalid(OpCode.DB_Error);
    }
}
