package missions.room.RoomManagerTests;

import Data.Data;
import DataAPI.OpCode;
import DomainMocks.MockRam;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.RoomManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;


public class RoomMangerTestsRealRam extends RoomManagerTestsAllStubs{
    void setUpMocks(){
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        classroomRepository=new ClassRoomRepositoryMock(dataGenerator);
        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
    }

    @Override
    void setUpAddRoom() {
        super.setUpAddRoom();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias());
    }

    @Test
    void testAddRoomInvalidWrongApiKey(){
        setUpAddRoom();
        apiKey=apiKey+"2";
        testAddRoomInValid(Data.VALID, OpCode.Wrong_Key);
        tearDownAddRoom();
    }
}
