package missions.room.Communications.Publisher;

import missions.room.Domain.Notification;

import java.util.ArrayList;


public class Publisher {

    private Sender sender;

    public Publisher(Sender sender) {
        this.sender=sender;
    }

    public void update(String userId, ArrayList<Notification> notification){
       sender.send(userId,notification);
    }
}
