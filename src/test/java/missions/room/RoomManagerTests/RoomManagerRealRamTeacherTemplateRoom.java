package missions.room.RoomManagerTests;

import Data.Data;
import DataAPI.OpCode;
import missions.room.Domain.Room;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomManagerRealRamTeacherTemplateRoom extends RoomManagerRealRamTeacherTemplate {

    @Override
    void setUpMocks(){}

    @Test
    void testAddRoomInvalidAlreadyExistStudentWithRoom(){
        setUpAddRoom();
        roomCrudRepository.save(dataGenerator.getRoom(Data.Valid_Student));
        testAddRoomInValid(Data.Valid_Student,OpCode.Already_Exist_Student);
        tearDown();
    }

    @Test
    void testAddRoomInvalidAlreadyExistGroupWithRoom(){
        setUpAddRoom();
        roomCrudRepository.save(dataGenerator.getRoom(Data.Valid_Group));
        testAddRoomInValid(Data.Valid_Group,OpCode.Already_Exist_Group);
        tearDown();
    }

    @Test
    void testAddRoomInvalidAlreadyExistClassroomWithRoom(){
        setUpAddRoom();
        roomCrudRepository.save(dataGenerator.getRoom(Data.Valid_Classroom));
        testAddRoomInValid(Data.Valid_Classroom,OpCode.Already_Exist_Class);
        tearDown();
    }

    @Override
    protected void testAddRoomValidTest(Data data){
        super.testAddRoomValidTest(data);
        Room dataRoom=dataGenerator.getRoom(data);
        Room room=roomCrudRepository.findAll().iterator().next();
        assertEquals(dataRoom.getName(),room.getName());
        assertEquals(dataRoom.getBonus(),room.getBonus());
        assertEquals(dataRoom.getRoomTemplate().getRoomTemplateId(), room.getRoomTemplate().getRoomTemplateId());
        assertEquals(dataRoom.getTeacher().getAlias(),room.getTeacher().getAlias());
        if(data==Data.Valid_Student){
            assertEquals(((StudentRoom)dataRoom).getParticipant().getAlias(),((StudentRoom)room).getParticipant().getAlias());
        }
        else if(data==Data.Valid_Group){
            assertEquals(((GroupRoom)dataRoom).getParticipant().getGroupName(),((GroupRoom)room).getParticipant().getGroupName());
        }
        else{
            assertEquals(((ClassroomRoom)dataRoom).getParticipant().getClassName(),((ClassroomRoom)room).getParticipant().getClassName());
        }
    }

    @Override
    protected void testAddRoomInValid(Data data, OpCode opcode){
        super.testAddRoomInValid(data,opcode);
        if(opcode!=OpCode.Already_Exist_Student&&opcode!=OpCode.Already_Exist_Group&&opcode!=OpCode.Already_Exist_Class){
            assertFalse(roomCrudRepository.findAll().iterator().hasNext());
        }
    }


}
