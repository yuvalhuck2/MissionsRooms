package missions.room.Communications.RealTime;

import missions.room.Service.RoomService;
import missions.room.Service.StudentRoomService;
import missions.room.Service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * 1. to check room with 2 users connected
 */


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private StudentRoomService studentRoomService;

    @Autowired
    private RoomService roomService;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic/","/queue/");
        registry.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
                .withSockJS();

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptorAdapter());
    }

    private class ChannelInterceptorAdapter implements ChannelInterceptor {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor =
                    MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (StompCommand.CONNECT.equals(accessor.getCommand())){
               String apiKey = accessor.getFirstNativeHeader("apiKey");
               accessor.setUser(new StompPrincipal(apiKey));
               userAuthenticationService.openWebSocket(apiKey);
            }

            return message;

        }

    }

    /**
     * deal with disconnection event
     */
    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) throws Exception {
        String userId = sessionDisconnectEvent.getUser().getName();
        studentRoomService.disconnectFromRooms(userId);
        roomService.disconnectFromRooms(userId);
        userAuthenticationService.closeWebsocket(userId);
    }

}
