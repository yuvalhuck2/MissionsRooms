package missions.room.PointsManagerTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.TeacherCrudRepository;
import CrudRepositories.UserCrudRepository;
import Data.Data;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Domain.Users.User;
import missions.room.Managers.ProfileMessagesManager;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class PointsManagerTestsRealRamTeacherRepo extends PointMangerTestsRealRam{

    @Autowired
    private TeacherRepo realTeacherRepo;

    @Autowired
    private TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected ClassroomRepository classroomRepository;

    @Mock
    private TeacherCrudRepository mockTeacherCrudRepository;

    @Override
    protected void initTeacherRepo(Teacher teacher, String studentAlias, Teacher supervisor) {
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_2Students_From_Different_Groups));
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        teacherCrudRepository.save(teacher);
        teacherCrudRepository.save(supervisor);
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(pointsManager,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    void testReducePointsFindTeacherByIdThrowsException() {
        when(mockTeacherCrudRepository.findById(anyString()))
                .thenThrow(new RuntimeException());
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(pointsManager,new TeacherRepo(mockTeacherCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        super.testReducePointsFindTeacherByIdThrowsException();
    }

    @Override
    protected void tearDownMocks() {
        teacherCrudRepository.deleteAll();
        classroomRepository.deleteAll();
        super.tearDownMocks();
    }
}
