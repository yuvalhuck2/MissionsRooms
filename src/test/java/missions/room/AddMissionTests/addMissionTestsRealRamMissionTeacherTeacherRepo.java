package missions.room.AddMissionTests;

import Data.Data;
import Domain.Mission;
import Domain.Ram;
import missions.room.Managers.AddMissionManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class addMissionTestsRealRamMissionTeacherTeacherRepo extends addMissionTestsRealRamMissionTeacher{

    @Override
    void setUpMocks() {
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
    }

    @Override
    void setUpAddMission(){
        try {
            Field managerRam = AddMissionManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            ram=(Ram)managerRam.get(addMissionManager);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        super.setUpAddMission();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias());
    }
}
