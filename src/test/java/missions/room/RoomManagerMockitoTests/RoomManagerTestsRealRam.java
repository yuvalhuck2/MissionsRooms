package missions.room.RoomManagerMockitoTests;


import Data.Data;
import missions.room.Domain.Ram;
import missions.room.Managers.ProfileMessagesManager;
import missions.room.Managers.RoomManager;
import missions.room.Managers.TeacherManager;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@Service
public class RoomManagerTestsRealRam extends RoomManagerTestsAllStubs{
    @Override
    protected void initRam(String alias){
        Ram realRam=new Ram();
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



    }
}
