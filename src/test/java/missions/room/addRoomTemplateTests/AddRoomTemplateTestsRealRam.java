package missions.room.addRoomTemplateTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.AddRoomTemplateManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class AddRoomTemplateTestsRealRam extends AddRoomTemplateTestsAllStubs {

    @Override
    void setUpMocks(){
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        addRoomTemplateManager=new AddRoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
    }

    @Override
    void setUpAddRoomTemplate(){
        super.setUpAddRoomTemplate();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias());
    }

    @Test
    void testMissionInvalidWrongApiKey(){
        setUpAddRoomTemplate();
        apiKey=apiKey+"2";
        checkWrongAddRoomTemplate(Data.VALID,OpCode.Wrong_Key);
        tearDownAddRoomTemplate();
    }
}
