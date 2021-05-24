package missions.room.RoomTemplateTests;

import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.RoomTemplateManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Service
public class RoomTemplateManagerTestsRealRam extends RoomTemplateManagerTestsAllStubs {

    @Override
    void setUpMocks(){
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        roomTemplateManager =new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
    }

    @Override
    void setUpAddRoomTemplate(){
        super.setUpAddRoomTemplate();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias());
    }

    @Override
    void setupSearchRoomTemplate(){
        super.setupSearchRoomTemplate();
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
