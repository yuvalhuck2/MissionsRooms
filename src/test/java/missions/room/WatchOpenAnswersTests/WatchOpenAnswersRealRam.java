package missions.room.WatchOpenAnswersTests;

import Data.Data;
import missions.room.Domain.Ram;
import missions.room.Managers.TeacherManager;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static Data.DataConstants.WRONG_TEACHER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

public class WatchOpenAnswersRealRam extends WatchOpenAnswersTestsAllStubs {
    @Override
    protected void initMocks(){
        Ram realRam=new Ram();
        super.initMocks();
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(missionManagerWithMockito,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }

        realRam.addApi(apiKey
                ,dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM)
                        .getAlias());
        realRam.addApi(NULL_USER_KEY
                ,WRONG_USER_NAME);
        realRam.addApi(NULL_TEACHER_KEY
                ,WRONG_TEACHER_NAME);
    }
}
