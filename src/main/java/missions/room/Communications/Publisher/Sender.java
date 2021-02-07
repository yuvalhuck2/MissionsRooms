package missions.room.Communications.Publisher;


import missions.room.Domain.Notification;

import java.util.ArrayList;

public interface Sender {
    void send(String userId, ArrayList<Notification> notification);
}
