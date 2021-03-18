package missions.room.PointsManagerTests;

import missions.room.Domain.Ram;
import missions.room.Managers.ProfileMessagesManager;
import missions.room.Managers.TeacherManager;

import java.lang.reflect.Field;

import static Data.DataConstants.NULL_USER_KEY;
import static Data.DataConstants.WRONG_USER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

public class PointMangerTestsRealRam extends PointsManagerTestsAllStubs {

    @Override
    protected void initRam(String studentAlias, String teacherAlias, String supervisorAlias){
        Ram realRam=new Ram();
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(pointsManager,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(studentApiKey
                ,studentAlias);
        realRam.addApi(teacherApiKey,
                teacherAlias);
        realRam.addApi(supervisorApiKey,
                supervisorAlias);
        realRam.addApi(NULL_USER_KEY
                , WRONG_USER_NAME);
    }
}
