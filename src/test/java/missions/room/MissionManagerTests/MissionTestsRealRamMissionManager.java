package missions.room.MissionManagerTests;

import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.Ram;
import missions.room.Managers.MissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class MissionTestsRealRamMissionManager extends MissionManagerTestsRealRam {

    @Override
    void setUpMocks() {
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
        missionManager =new MissionManager(ram,teacherCrudRepository,missionCrudRepository);
    }

    @Test
    void testMissionInvalidWrongMission(){
        setUpAddMission();
        missionString=gson.toJson(dataGenerator.getMission(Data.NULL_QUESTION_DETERMINISTIC), Mission.class);
        checkWrongAddMission(OpCode.Wrong_Question);
        tearDownAddMission();
    }


}
