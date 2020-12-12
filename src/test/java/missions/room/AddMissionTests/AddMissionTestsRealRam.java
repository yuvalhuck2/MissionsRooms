package missions.room.AddMissionTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import DomainMocks.MissionMock;
import missions.room.Managers.AddMissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class AddMissionTestsRealRam extends AddKnownAnswerMissionTestsAllStubs {

    @Override
    void setUpMocks() {
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        missionString=gson.toJson(new MissionMock(), Mission.class);
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository,missionCrudRepository);
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
