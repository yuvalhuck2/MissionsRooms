package missions.room.CsvManagerTests;

import missions.room.Domain.Ram;
import missions.room.Managers.ITManager;
import missions.room.Managers.UploadCsvManager;

import java.lang.reflect.Field;

import static Data.DataConstants.NULL_USER_KEY;
import static Data.DataConstants.WRONG_USER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

public class CsvManagerTestsRealRam extends CsvManagerTestsAllStubs{
    protected Ram realRam;

    @Override
    protected void initRam(String itAlias) {
        realRam = new Ram();
        try {
            Field managerRam = UploadCsvManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(uploadCsvManager, realRam);

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
