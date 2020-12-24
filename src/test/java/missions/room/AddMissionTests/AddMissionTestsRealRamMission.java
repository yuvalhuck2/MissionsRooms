package missions.room.AddMissionTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockTeacherMock;
import missions.room.Managers.AddMissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class AddMissionTestsRealRamMission extends AddMissionTestsRealRam {

    @Override
    void setUpMocks() {
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository,missionCrudRepository);
    }

    @Test
    void testMissionInvalidWrongMission(){
        setUpAddMission();
        missionString=gson.toJson(dataGenerator.getMission(Data.NULL_QUESTION_DETERMINISTIC), Mission.class);
        checkWrongAddMission(OpCode.Wrong_Question);
        tearDownAddMission();
    }


}
