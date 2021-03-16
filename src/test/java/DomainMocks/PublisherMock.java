package DomainMocks;

import missions.room.Communications.Publisher.Publisher;
import missions.room.Communications.Publisher.Sender;
import missions.room.Domain.Notifications.Notification;

import java.util.ArrayList;
import java.util.HashMap;

public class PublisherMock extends Publisher {

    private HashMap<String,ArrayList<Notification>> notifications;


    public PublisherMock(Sender sender) {
        super(sender);
    }

    public PublisherMock() {
        notifications=new HashMap<>();
    }

    @Override
    public void updateAll(String userId, ArrayList<Notification> notifications) {
        if(!this.notifications.containsKey(userId)){
            this.notifications.put(userId,new ArrayList<>());
        }
        this.notifications.get(userId).addAll(notifications);
    }

    @Override
    public void update(String userId, Notification notification) {
        if(!notifications.containsKey(userId)){
            notifications.put(userId,new ArrayList<>());
        }
        this.notifications.get(userId).add(notification);
    }

    public ArrayList<Notification> getNotifications(String userId){
        return notifications.get(userId);
    }

    public HashMap<String, ArrayList<Notification>> getAllNotifications() {
        return notifications;
    }

    public void clear() {
        notifications.clear();
    }

    public boolean isEmpty() {
        return notifications.isEmpty();
    }
}
