package missions.room.Communications.Publisher;

import missions.room.Domain.Notification;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;

public class SpringSender implements Sender{

    private final SimpMessageSendingOperations messagingTemplate;

    public SpringSender(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate=messagingTemplate;
    }

    public void send(String userId, ArrayList<Notification> notification){
        messagingTemplate.convertAndSendToUser( userId,"/queue/greetings", notification);
    }
}
