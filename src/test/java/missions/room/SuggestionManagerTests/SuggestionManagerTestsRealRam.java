package missions.room.SuggestionManagerTests;


import Data.Data;
import missions.room.Domain.Ram;
import missions.room.Managers.TeacherManager;

import java.lang.reflect.Field;

import static Data.DataConstants.NULL_STUDENT_KEY;
import static Data.DataConstants.WRONG_STUDENT_NAME;
import static org.junit.jupiter.api.Assertions.fail;


public class SuggestionManagerTestsRealRam extends SuggestionManagerTestsAllStubs {


    @Override
    protected void initMocks(){
        Ram realRam=new Ram();
        super.initMocks();
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(suggestionManager,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(apiKey,dataGenerator.getStudent(Data.VALID).getAlias());
        realRam.addApi(NULL_STUDENT_KEY,WRONG_STUDENT_NAME);
    }
}
