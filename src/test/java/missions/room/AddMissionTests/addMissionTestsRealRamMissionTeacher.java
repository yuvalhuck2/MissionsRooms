package missions.room.AddMissionTests;

import Data.Data;
import Domain.Mission;
import Domain.Ram;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockTeacherMock;
import missions.room.Managers.AddMissionManager;
import org.springframework.stereotype.Service;

@Service
public class addMissionTestsRealRamMissionTeacher extends AddMissionTestsRealRamMission {

    @Override
    void setUpMocks() {
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository);
    }

}
