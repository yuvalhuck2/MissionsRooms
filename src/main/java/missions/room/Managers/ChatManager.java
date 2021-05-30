package missions.room.Managers;


import DataObjects.APIObjects.ChatMessageData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Communications.Publisher.Publisher;
import missions.room.Communications.Publisher.SinglePublisher;
import missions.room.Domain.Notifications.NonPersistenceNotification;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Users.SchoolUser;
import missions.room.Repo.RoomRepo;
import missions.room.Repo.SchoolUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class ChatManager {

    @Autowired
    protected SchoolUserRepo schoolUserRepo;

    @Autowired
    private RoomRepo roomRepo;

    private static Publisher publisher;

    protected Ram ram;

    public ChatManager(){
        this.ram=new Ram();
    }

    public static void initPublisher(){
        publisher= SinglePublisher.getInstance();
    }

    public void initPublisher(Publisher publisher){
        ChatManager.publisher = publisher;
    }

    public Response<String> sendMessage(String apiKey, ChatMessageData message, String  roomId){
        if(apiKey==null){
            return new Response<>("", OpCode.Wrong_Key);
        }
        String alias1 = ram.getAlias(apiKey);
        if(alias1==null){
            return new Response<>("", OpCode.Wrong_Key);
        }
        ram.addChatMessage(roomId,message);
        Room room;
        if(ram.isRoomExist(roomId)){
            room=ram.getRoom(roomId);
        }
        else{
            Response<Room> roomResponse=roomRepo.findRoomForRead(roomId);
            if(roomResponse.getReason()!=OpCode.Success){
                return new Response<>("",roomResponse.getReason());
            }
            else if(roomResponse.getValue() == null){
                return new Response<>("", OpCode.Not_Exist_Room);
            }
            else{
                room=roomResponse.getValue();
            }
        }
        NonPersistenceNotification<ChatMessageData> notification = new NonPersistenceNotification<>(OpCode.Update_Chat, message);
        for(String alias:room.getConnectedUsersAliases()){
            String otherApiKey = ram.getApiKey(alias);
            if(otherApiKey!=null && !apiKey.equals(otherApiKey)) {
                publisher.update(otherApiKey, notification);
            }
        }
        if(room.isTeacherConnect()&&!ram.getApiKey(room.getTeacher().getAlias()).equals(apiKey)) {
            String apiTeacher = ram.getApiKey(room.getTeacher().getAlias());
            publisher.update(apiTeacher, notification);
        }

        return new Response<>("",OpCode.Success);


    }

    public Response<String> enterChat(String apiKey,String roomId){
        if(apiKey ==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        String alias = ram.getAlias(apiKey);
        if(alias ==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        Response<SchoolUser> schoolUserResponse=schoolUserRepo.findUserForRead(alias);
        if(schoolUserResponse.getReason()!=OpCode.Success){
            return new Response<>(null,schoolUserResponse.getReason());
        }
        if(schoolUserResponse.getValue() == null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        OpCode response=ram.connectToRoom(roomId,ram.getAlias(apiKey));
        if(response!=OpCode.Teacher){
            return new Response<>(null,response);
        }
        return new Response<>(schoolUserResponse.getValue().getFullName(),OpCode.Success);
    }




}
