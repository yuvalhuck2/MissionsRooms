package missions.room.SuggestionManagerTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import missions.room.Managers.StudentTeacherManager;
import missions.room.Repo.StudentRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class SuggestionManagerTestsRealRamStudentRepo extends SuggestionManagerTestsRealRam{

    @Autowired
    private StudentRepo realStudentRepo;

    @Autowired
    protected ClassroomRepository classroomRepo;

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Override
    protected void initMocks(){
        classroomRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        super.initMocks();
        try {
            Field studentRepo = StudentTeacherManager.class.getDeclaredField("studentRepo");
            studentRepo.setAccessible(true);
            studentRepo.set(suggestionManager,realStudentRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    @Test
    void addSuggestionFindStudentByIdThrowsExceptionTest(){
        when(mockStudentCrudRepository.findById(anyString()))
                .thenThrow(new DataIntegrityViolationException(""));
        realStudentRepo=new StudentRepo(mockStudentCrudRepository);
        try {
            Field studentRepo = StudentTeacherManager.class.getDeclaredField("studentRepo");
            studentRepo.setAccessible(true);
            studentRepo.set(suggestionManager,realStudentRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testAddSuggestionInvalid(OpCode.DB_Error);
    }


    @Override
    @AfterEach
    void tearDown() {
        teacherCrudRepository.deleteAll();
        classroomRepo.deleteAll();
        super.tearDown();
    }
}
