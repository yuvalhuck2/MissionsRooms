package missions.room.PointsManagerTests;

import CrudRepositories.UserCrudRepository;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.PointsManager;l
import missions.room.Repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PointsManagerTestsAllReal extends PointsManagerRealRamTeacherStudentClassroomRepo {

    @Autowired
    private UserRepo realUserRepo;

    @Mock
    private UserCrudRepository mockUserCrudRepository;

    @Override
    protected void initUserRepo(Student student, Teacher teacher, Teacher supervisor) {
        try {
            Field userRepo = PointsManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(pointsManager,realUserRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    void testWatchTableIsExistUserThrowsException() {
        try {
            Field userRepo = PointsManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(pointsManager,new UserRepo(mockUserCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        when(mockUserCrudRepository.existsById(anyString()))
                .thenThrow(new RuntimeException());
        super.testWatchTableIsExistUserThrowsException();
    }
}
