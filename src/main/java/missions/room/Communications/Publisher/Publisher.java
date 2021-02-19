package missions.room.Communications.Publisher;

import missions.room.Domain.Notifications.Notification;

import java.util.ArrayList;


public class Publisher {

    private Sender sender;

    public Publisher(Sender sender) {
        this.sender=sender;
    }

    public Publisher() {
    }

    public void updateAll(String userId, ArrayList<Notification> notification){
       sender.send(userId,notification);
    }

    public void update(String userId, Notification notification){
        sender.send(userId,notification);
    }
}
