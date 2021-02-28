package missions.room.TriviaManagerTests;

import Data.Data;
import missions.room.Domain.Ram;
import missions.room.Managers.TeacherManager;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static Data.DataConstants.WRONG_TEACHER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

public class TriviaManagerTestsRealRam extends TriviaManagerTestsAllStubs{

    @Override
    protected void initMocks(){
        Ram realRam=new Ram();
        super.initMocks();
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(triviaManager,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(studentApiKey
                ,dataGenerator.getStudent(Data.VALID)
                        .getAlias());
        realRam.addApi(teacherApiKey
                ,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD)
                        .getAlias());
        realRam.addApi(NULL_STUDENT_KEY
                ,WRONG_STUDENT_NAME);
        realRam.addApi(NULL_TEACHER_KEY
                ,WRONG_TEACHER_NAME);
    }
}