package missions.room.Managers;

import DataAPI.MessageData;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.UserProfileData;
import ExternalSystems.UniqueStringGenerator;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Message;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.BaseUser;
import missions.room.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static Utils.Utils.checkString;

@Service
@CommonsLog
public class ProfileMessagesManager {

    @Autowired
    private UserRepo userRepo;

    private Ram ram;

    public ProfileMessagesManager() {
        ram=new Ram();
    }

    /**
     * req 3.4.1 - send message
     * @param apiKey - authentication object
     * @param message - message to send to the other user
     * @param alias - target user alias
     * @return if the message was sent successfully
     */
    @Transactional
    public Response<Boolean> sendMessage(String apiKey, String message, String alias){
        if(!checkString(message)){
            return new Response<>(false,OpCode.Empty);
        }
        String userAlias=ram.getAlias(apiKey);
        Response<Boolean> APIResponse=userRepo.isExistsById(userAlias);
        if(APIResponse.getReason()!=OpCode.Success){
            log.warn(String.format("There was a problem from kind %s when trying to find user %s",
                    APIResponse.getReason(),
                    userAlias));
            return new Response<>(false,APIResponse.getReason());
        }
        if(!APIResponse.getValue()){
            return new Response<>(false,OpCode.Not_Exist);
        }
        Response<BaseUser> userResponse=userRepo.findUserForWrite(alias);
        if(userResponse.getReason()!=OpCode.Success) {
            log.warn(String.format("There was a problem from kind %s when trying to find user %s",
                    userResponse.getReason(),
                    alias));
            return new Response<>(false,userResponse.getReason());
        }

        BaseUser baseUser =userResponse.getValue();
        if(baseUser ==null){
            return new Response<>(false,OpCode.Not_Exist);
        }
        baseUser.addMessage(new Message(UniqueStringGenerator.getTimeNameCode("ms"),
                message,
                userAlias));
        Response<BaseUser> saveUser= userRepo.save(baseUser);

        return new Response<>(saveUser.getReason()==OpCode.Success,
                saveUser.getReason());
    }


    /**
     * req 3.4.2 - view my messages
     * @param apiKey - authentication object
     * @return messages list
     */
    public Response<List<MessageData>> viewMessages(String apiKey){
        String userAlias=ram.getAlias(apiKey);
        Response<BaseUser> userResponse=userRepo.findUser(userAlias);
        if(userResponse.getReason()!=OpCode.Success) {
            log.warn(String.format("There was a problem from kind %s when trying to find user %s",
                    userResponse.getReason(),
                    userAlias));
            return new Response<>(null,userResponse.getReason());
        }

        return new Response<>(userResponse.
                getValue().
                getMessages().
                parallelStream().
                map(Message::getData).
                sorted(new MessageDataComparator()).
                collect(Collectors.toList()),
                OpCode.Success);
    }

    /**
     * req 3.4.2 - delete a message
     * @param apiKey - authentication object
     * @param messageId - message id
     * @return if the message was deleted successfully
     */
    @Transactional
    public Response<Boolean> deleteMessage(String apiKey,String messageId){
        String userAlias=ram.getAlias(apiKey);
        Response<BaseUser> userResponse=userRepo.findUserForWrite(userAlias);
        if(userResponse.getReason()!=OpCode.Success) {
            log.warn(String.format("There was a problem from kind %s when trying to find user %s",
                    userResponse.getReason(),
                    userAlias));
            return new Response<>(false,userResponse.getReason());
        }

        BaseUser baseUser = userResponse.getValue();
        if(baseUser ==null){
            return new Response<>(false,OpCode.Not_Exist);
        }
        Response<Boolean> deleteMessageResponse= baseUser.deleteMessage(messageId);
        if(deleteMessageResponse.getReason()!=OpCode.Success){
            return deleteMessageResponse;
        }

        OpCode reason=userRepo.save(baseUser).getReason();
        return new Response<>(reason==OpCode.Success,reason);
    }

    private Response<List<UserProfileData>> getAllUsersProfiles() {
        Response<List<BaseUser>> users= userRepo.findAllUsers();
        if(users.getReason()!=OpCode.Success){
            log.error("Function getAllUsersProfiles: connection to the DB lost");
        }
        return new Response<>(users.getValue()
                .stream()
                .map((BaseUser::getProfileData))
                .collect(Collectors.toList()),
                OpCode.Success);
    }

    /**
     * req 3.5 - watch user profile
     * @param apiKey - authentication object
     * @return user profile details
     */
    public Response<List<UserProfileData>> watchProfile(String apiKey){
        String userAlias=ram.getAlias(apiKey);
        Response<Boolean> APIResponse=userRepo.isExistsById(userAlias);
        if(APIResponse.getReason()!=OpCode.Success){
            log.warn(String.format("There was a problem from kind %s when trying to find user %s",
                    APIResponse.getReason(),
                    userAlias));
            return new Response<>(null,APIResponse.getReason());
        }
        if(!APIResponse.getValue()){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return getAllUsersProfiles();
    }

    static class MessageDataComparator implements Comparator<MessageData> {
        @Override
        public int compare(MessageData o1, MessageData o2) {
            return o2.getId().compareTo(o1.getId());
        }
    }
}


