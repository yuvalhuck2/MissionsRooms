package missions.room.WatchOpenAnswersTests;

import Data.Data;
import DataAPI.Response;
import missions.room.Domain.Classroom;
import missions.room.Managers.MissionManager;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

public class WatchOpenAnswersRealRamTeacherRepo extends WatchOpenAnswersRealRam{

    @Autowired
    private ClassroomRepo realClassroomRepo;

    @Autowired
    private TeacherRepo realTeacherRepo;

    @Override
    protected void initMocks(){
        super.initMocks();
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(missionManagerWithMockito,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        Response<Classroom> ress = realClassroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        realTeacherRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
    }
}
