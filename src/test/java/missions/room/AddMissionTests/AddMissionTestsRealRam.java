package missions.room.AddMissionTests;

import Data.Data;
import DataAPI.OpCode;
import Domain.Mission;
import Domain.Ram;
import DomainMocks.MissionMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockTeacherMock;
import missions.room.Managers.AddMissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class AddMissionTestsRealRam extends addMissionTestsAllStubs {

    @Override
    void setUpMocks() {
        teacherCrudRepository=new TeacherCrudRepositoryMockTeacherMock(dataGenerator);
        ram=new Ram();
        missionString=gson.toJson(new MissionMock(), Mission.class);
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository);
    }

    @Override
    void setUpAddMission(){
        super.setUpAddMission();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias());
    }

    @Test
    void testMissionInvalidWrongApiKey(){
        setUpAddMission();
        apiKey=apiKey+"2";
        checkWrongAddMission(OpCode.Wrong_Key);
        tearDownAddMission();
    }
}
