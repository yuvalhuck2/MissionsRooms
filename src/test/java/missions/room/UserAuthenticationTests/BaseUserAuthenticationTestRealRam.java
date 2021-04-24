package missions.room.UserAuthenticationTests;

import missions.room.Domain.Ram;
import missions.room.Domain.Users.Student;
import missions.room.Managers.TeacherManager;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static Data.DataConstants.WRONG_TEACHER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

public class BaseUserAuthenticationTestRealRam extends BaseUserAuthenticationTestsAllStubs {

    @Override
    protected void initRam(Student student){
        Ram realRam=new Ram();
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(userAuthenticationManager,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(validApiKey
                ,student.getAlias());
        realRam.addApi(NULL_TEACHER_KEY
                ,WRONG_TEACHER_NAME);
    }
}
