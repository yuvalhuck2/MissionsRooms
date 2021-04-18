package missions.room.Communications.RealTime;

import DataAPI.OpCode;
import missions.room.Communications.Publisher.Publisher;
import missions.room.Communications.Publisher.SinglePublisher;
import missions.room.Communications.Publisher.SpringSender;
import missions.room.Domain.Notifications.NonPersistenceNotification;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;


@Controller
public class WebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final Publisher pub;

    public WebSocketController(SimpMessageSendingOperations messagingTemplate) throws InterruptedException {
        this.messagingTemplate = messagingTemplate;
        pub = new Publisher(new SpringSender(messagingTemplate));
        SinglePublisher.initPublisher(pub);
    }

    @MessageMapping("/hello")
    @SendToUser("/queue/greetings")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        pub.updateAll("apiKey",new ArrayList<>(Collections.singleton(
                new NonPersistenceNotification<>(OpCode.Success,"this is web socket message")
        )));
        return "";
    }

    @MessageExceptionHandler
    @SendToUser("/error")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }






}
