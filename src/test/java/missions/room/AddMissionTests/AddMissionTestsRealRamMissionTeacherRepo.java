package missions.room.AddMissionTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Managers.AddMissionManager;
import missions.room.Repo.MissionRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AddMissionTestsRealRamMissionTeacherRepo extends AddMissionTestsRealRamMission {

    @Override
    void setUpMocks() {
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
        try {
            Field missionRepo = AddMissionManager.class.getDeclaredField("missionRepo");
            missionRepo.setAccessible(true);
            missionRepo.set(addMissionManager,new MissionRepo(missionCrudRepository));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
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
    }

    @Test
    void testMissionInvalidWrongTeacher(){
        setUpAddMission();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getPassword());
        checkWrongAddMission(OpCode.Not_Exist);
        tearDownAddMission();
    }

}
