package missions.room.Communications.Controllers;

import DataObjects.APIObjects.ReducePointsData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.Not_Exist;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

@RunWith(SpringRunner.class)
class PointsControllerTest extends ControllerTest {

    @Test
    void testWatchTable() {
        String body = gson.toJson(roomIdAndApiKey);
        testControllerWithBody(WATCH_POINTS_TABLE, body, Not_Exist, null);
    }

    @Test
    void testReducePoints() {
        ReducePointsData reducePointsData = new ReducePointsData("apiKey", "alias", 20);
        String body = gson.toJson(reducePointsData);
        testControllerWithBody(REDUCE_POINTS, body, Wrong_Key, false);
    }
}