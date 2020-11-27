package Service;

import DataAPI.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.ws.Response;

public class ServiceAPI {

    /**
     * req 2.2 - register
     * @param details - user details
     * @return if register succeeded
     */
    public Response<Boolean> register (RegisterDetailsData details){
        throw new NotImplementedException();
    }

    /**
     * req 2.3 -login
     * @param mail - user mail
     * @param password - user password
     * @return API key if login succeeded
     */
    public Response<String> login (String mail, String password){
        throw new NotImplementedException();
    }

    /**
     * req 3.1 - watch rank Table details
     * @param auth - authentication object
     * @param tableType - personal, group or class
     * @return the record table
     */
    public Response<RecordTableData> watchTable (Auth auth, RoomType tableType){
        throw new NotImplementedException();
    }

    /**
     * req 3.2
     * TODO talk about delete profile
     */


    /**
     * req 3.3
     * @param auth - authentication object
     * @param newPassword - the new password to change to
     * @return if the password was changed
     */
    public Response<Boolean> changePassword (Auth auth,String newPassword){
        throw new NotImplementedException();
    }


    /**
     * req 3.4 - send message
     * @param auth - authentication object
     * @param message - message to send to the other user
     * @param mail - target user mail
     * @return if the message was sent successfully
     */
    public Response<Boolean> sendMessage(Auth auth,String message,String mail){
        throw new NotImplementedException();
    }

    /**
     * req 3.5 - watch user profile
     * @param auth - authentication object
     * @param mail - target user mail
     * @return user profile details
     */
    public Response<UserProfileData> watchProfile(Auth auth, String mail){
        throw new NotImplementedException();
    }

    /**
     * req 3.6.1 - watch room details
     * @param auth - authentication object
     * @param roomId - room id
     * @return the mission details of the given room
     * TODO check on database if can generate unique string
     */
    public Response<MissionDetailsData> watchMissionDetails (Auth auth, int roomId){
        throw new NotImplementedException();
    }

    /**
     * 3.6.2.1 - answer open question mission
     * @param auth - authentication object
     * @param roomId - room id
     * @param answer - verbal answer of the open question
     * @param file - file attached to the solution
     * @return if the answer was accepted successfully
     */
    public Response<Boolean> answerOpenQuestionMission(Auth auth, int roomId,String answer,String file){
        throw new NotImplementedException();
    }




}
