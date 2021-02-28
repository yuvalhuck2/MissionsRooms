package missions.room.RoomTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RoomTestsReal {

    private Room room;

    private DataGenerator dataGenerator;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        room=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        room.connect(dataGenerator.getStudent(Data.VALID).getAlias());
    }

    @Test
    void testDisconnectNoUsersRemained(){
        Response<String> roomResponse= room.disconnect(dataGenerator.getStudent(Data.VALID).getAlias());
        assertEquals(roomResponse.getReason(), OpCode.Delete);
        assertNull(roomResponse.getValue());
    }

    @Test
    void testDisconnectMissionInChargeChanged(){
        String alias=dataGenerator.getStudent(Data.VALID2).getAlias();
        room.connect(alias);
        Response<String> roomResponse= room.disconnect(dataGenerator.getStudent(Data.VALID).getAlias());
        assertEquals(roomResponse.getReason(), OpCode.Success);
        assertEquals(alias,roomResponse.getValue());
    }

    @Test
    void testDisconnectNoMissionInChargeChanged(){
        String alias=dataGenerator.getStudent(Data.VALID2).getAlias();
        room.connect(alias);
        Response<String> roomResponse= room.disconnect(alias);
        assertEquals(roomResponse.getReason(), OpCode.Success);
        assertNull(roomResponse.getValue());
    }
}
