package missions.room.TriviaManagerTests;

import Data.Data;
import missions.room.Managers.StudentTeacherManager;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.StudentRepo;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

public class TriviaManagerTestsRealRamTeacher extends  TriviaManagerTestsRealRam {

    @Autowired
    private TeacherRepo realTeacherRepo;

    @Override
    protected void initMocks(){
        super.initMocks();
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(triviaManager,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realTeacherRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
    }
}