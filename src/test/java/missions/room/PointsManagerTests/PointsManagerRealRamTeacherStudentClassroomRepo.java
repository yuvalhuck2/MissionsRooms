package missions.room.PointsManagerTests;

import missions.room.Managers.PointsManager;
import missions.room.Repo.ClassroomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class PointsManagerRealRamTeacherStudentClassroomRepo extends PointsManagerTestsRealRamTeacherStudentRepo{

    @Autowired
    private ClassroomRepo realClassroomRepo;

    @Override
    protected void initClassroomRepo() {
        try {
            Field studentRepo = PointsManager.class.getDeclaredField("classroomRepo");
            studentRepo.setAccessible(true);
            studentRepo.set(pointsManager,realClassroomRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

}
