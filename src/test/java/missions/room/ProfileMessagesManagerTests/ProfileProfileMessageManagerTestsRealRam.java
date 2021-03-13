package missions.room.ProfileMessagesManagerTests;

import missions.room.Domain.Ram;
import missions.room.Managers.ProfileMessagesManager;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.fail;

@Service
public class ProfileProfileMessageManagerTestsRealRam extends ProfileMessageManagerTestsAllStubs {

    @Override
    protected void initRam(String alias){
        Ram realRam=new Ram();
        try {
            Field managerRam = ProfileMessagesManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(profileMessagesManager,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(studentApiKey
                ,alias);
        realRam.addApi(student2ApiKey,
                targetAlias);
        realRam.addApi(NULL_USER_KEY
                , WRONG_USER_NAME);
    }
}
