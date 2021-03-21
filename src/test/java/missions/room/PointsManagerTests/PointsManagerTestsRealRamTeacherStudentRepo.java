package missions.room.PointsManagerTests;

import CrudRepositories.StudentCrudRepository;
import Data.Data;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.PointsManager;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class PointsManagerTestsRealRamTeacherStudentRepo extends PointsManagerTestsRealRamTeacherRepo {

    @Autowired
    protected StudentRepo realStudentRepo;

    @Mock
    private StudentCrudRepository mockStudentCrudRepository;

    @Override
    protected void initStudentRepo(Student student) {
        try {
            Field studentRepo = PointsManager.class.getDeclaredField("studentRepo");
            studentRepo.setAccessible(true);
            studentRepo.set(pointsManager,realStudentRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    private void setUpMockStudentRepo() {
        try {
            Field studentRepo = PointsManager.class.getDeclaredField("studentRepo");
            studentRepo.setAccessible(true);
            studentRepo.set(pointsManager,new StudentRepo(mockStudentCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    void testReducePointsFindStudentForWriteThrowsException() {
        setUpMockStudentRepo();
        when(mockStudentCrudRepository.findUserForWrite(anyString()))
                .thenThrow(new RuntimeException());
        super.testReducePointsFindStudentForWriteThrowsException();
    }

    @Test
    @Override
    void testReducePointsSaveStudentThrowsException() {
        setUpMockStudentRepo();
        when(mockStudentCrudRepository.findUserForWrite(anyString()))
                .thenReturn(dataGenerator.getStudent(Data.VALID));
        when(mockStudentCrudRepository.save(any()))
                .thenThrow(new RuntimeException());
        super.testReducePointsSaveStudentThrowsException();
    }
}
