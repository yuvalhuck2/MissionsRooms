package missions.room.TriviaManagerTests;

import Data.Data;
import missions.room.Domain.Ram;
import missions.room.Managers.TeacherManager;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static Data.DataConstants.WRONG_TEACHER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

public class TriviaManagerTestsRealRam extends TriviaManagerTestsAllStubs{
    private Ram realRam;
    @Override
    protected void initMocks(){
        this.realRam = new Ram();
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
        realRam.addApi(NULL_USER_KEY
                ,WRONG_USER_NAME);
        realRam.addApi(NULL_TEACHER_KEY
                ,WRONG_TEACHER_NAME);
    }
    @AfterEach
    public void clearRam(){
        realRam.clearRam();
    }
}
