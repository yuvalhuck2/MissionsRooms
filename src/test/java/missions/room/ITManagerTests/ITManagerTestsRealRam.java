package missions.room.ITManagerTests;

import Data.Data;
import missions.room.Domain.Ram;
import missions.room.Managers.ITManager;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static Data.DataConstants.NULL_USER_KEY;
import static Data.DataConstants.WRONG_USER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

@Service
public class ITManagerTestsRealRam extends ITManagerTestsAllStubs {

    protected Ram realRam;

    @Override
    protected void initRam(String itAlias) {
        realRam = new Ram();
        try {
            Field managerRam = ITManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(itManager, realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(NULL_USER_KEY
                , WRONG_USER_NAME);
        realRam.addApi(NULL_USER_KEY
                , WRONG_USER_NAME);
        realRam.addApi(ITApiKey,
                itAlias);
        realRam.addAlias(ITApiKey);
    }
}
