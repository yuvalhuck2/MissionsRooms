package missions.room.Communications.Publisher;

import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.RoomManager;

public class SinglePublisher {

    private static Publisher publisher=null;
    public static  Publisher getInstance() {
        return publisher;
    }

    public static void initPublisher(Publisher pub){
        publisher=pub;
        ManagerRoomStudent.initPublisher();
        RoomManager.initPublisher();

    }



}
