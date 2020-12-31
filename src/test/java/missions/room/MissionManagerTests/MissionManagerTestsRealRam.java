package missions.room.MissionManagerTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import DomainMocks.MissionMock;
import missions.room.Managers.MissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class MissionManagerTestsRealRam extends MissionManagerTestsAllStubs {

    @Override
    void setUpMocks() {
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        missionString=gson.toJson(new MissionMock(), Mission.class);
        missionManager =new MissionManager(ram,teacherCrudRepository,missionCrudRepository);
    }

    @Override
    void setUpAddMission(){
        super.setUpAddMission();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias());
    }

    @Override
    void setupSearch(){
        super.setupSearch();
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
