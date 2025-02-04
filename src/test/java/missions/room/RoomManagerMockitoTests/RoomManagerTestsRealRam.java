package missions.room.RoomManagerMockitoTests;


import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.Ram;
import missions.room.Managers.TeacherManager;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@Service
public class RoomManagerTestsRealRam extends RoomManagerTestsAllStubs{

    protected Ram realRam;

    @Override
    protected void initRam(String alias){
        realRam=new Ram();
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(roomManager,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(teacherApi,alias);
        realRam.addApi(VALID2_TEACHER_APIKEY,dataGenerator.getTeacher(Data.VALID_WITHOUT_CLASSROOM).getAlias());
        realRam.addRoom(room);
        realRam.addApi(VALID3_TEACHER_APIKEY,dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM2).getAlias());




    }

    @Override
    @Test
    protected void testCloseRoomConnectedStudents(){
        realRam.deleteRoom(room.getRoomId());
        realRam.addRoom(dataGenerator.getRoom(Data.Valid_Student));
        Response<Boolean> response=roomManager.closeRoom(teacherApi,room.getRoomId());
        assertFalse(response.getValue());
        assertEquals(response.getReason(),OpCode.CONNECTED_STUDENTS);
    }

    @Override
    @Test
    protected void testCloseRoomHappy(){
        for(String connected:room.getConnectedUsersAliases()){
            room.disconnect(connected);
        }
        realRam.deleteRoom(room.getRoomId());
        realRam.addRoom(room);
        Response<Boolean> response=roomManager.closeRoom(teacherApi,room.getRoomId());
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
    }
}
