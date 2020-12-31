package missions.room.RoomManagerTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import missions.room.Managers.RoomManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomManagerRealRamTeacherTemplate extends RoomManagerRealRamTeacher {

    @Override
    void setUpMocks(){
        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
    }

    @Test
    void testAddRoomNotExistTemplate(){
        setUpAddRoom();
        testAddRoomInValid(Data.NOT_EXIST_TEMPLATE, OpCode.Not_Exist_Template);
        tearDownAddRoom();
    }


}
