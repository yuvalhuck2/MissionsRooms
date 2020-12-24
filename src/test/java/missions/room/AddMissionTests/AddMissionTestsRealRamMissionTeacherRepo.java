package missions.room.AddMissionTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Managers.AddMissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class AddMissionTestsRealRamMissionTeacherRepo extends AddMissionTestsRealRamMission {

    @Override
    void setUpMocks() {
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
        ram=new Ram();
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository,missionCrudRepository);
    }


    @Test
    void testMissionInvalidWrongTeacher(){
        setUpAddMission();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getPassword());
        checkWrongAddMission(OpCode.Not_Exist);
        tearDownAddMission();
    }

}
