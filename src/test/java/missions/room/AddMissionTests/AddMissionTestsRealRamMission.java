package missions.room.AddMissionTests;

import Data.Data;
import DataAPI.OpCode;
import Domain.Mission;
import Domain.Ram;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockTeacherMock;
import missions.room.Managers.AddMissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class AddMissionTestsRealRamMission extends AddMissionTestsRealRam {

    @Override
    void setUpMocks() {
        teacherCrudRepository=new TeacherCrudRepositoryMockTeacherMock(dataGenerator);
        ram=new Ram();
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository);
    }

    @Test
    void testMissionInvalidWrongMission(){
        setUpAddMission();
        missionString=gson.toJson(dataGenerator.getMission(Data.NULL_QUESTION_DETERMINISTIC), Mission.class);
        checkWrongAddMission(OpCode.Wrong_Question);
        tearDownAddMission();
    }


}
