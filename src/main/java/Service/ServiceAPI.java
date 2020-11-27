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
     * req 3.6.1 - watch details of the room
     * @param auth - authentication object
     * @param roomId - room id
     * @return the mission details of the given room
     * TODO check on database if can generate unique string
     */
    public Response<RoomDetailsData> watchRoomDetails (Auth auth, int roomId){
        throw new NotImplementedException();
    }

    /**
     * req 3.6.2.1 - answer open question mission
     * @param auth - authentication object
     * @param roomId - room id
     * @param answer - verbal answer of the open question
     * @param file - file attached to the solution
     * @return if the answer was accepted successfully
     */
    //TODO real time notifications to move the other clients a room
    public Response<Boolean> answerOpenQuestionMission(Auth auth, int roomId,String answer,String file){
        throw new NotImplementedException();
    }


    //TODO talk about discussion mission
    /**
     * req 3.6.2.2 - answer open discussion mission
     * @return if the answer was accepted successfully
     */

    /**
     * req 3.6.2.3 - answer deterministic question mission
     * @param auth - authentication object
     * @param roomId - room id
     * @param answer - answer for the question
     * @return if the answer was correct
     */
    public Response<Boolean> answerDeterministicQuestion(Auth auth,int roomId,String answer){
        throw new NotImplementedException();
    }


    /**
     * req 3.6.2.4.1 - fill true and lie sentences
     * @param auth - authentication object
     * @param roomId - room id
     * @param trueSentence - the correct sentence about the student
     * @param falseSentence - incorrect sentence about the student
     * @return if the sentences were added to the mission
     */
    public Response<Boolean> answerTrueLieQuestion(Auth auth, int roomId,String trueSentence, String falseSentence){
        throw new NotImplementedException();
    }


    /**
     * req 3.6.2.4.2 - answer true lie sentence
     * @param auth - authentication object
     * @param roomId - room id
     * @param mail - th mail of the
     * @param trueAnswer - true if the sentence the student think the first sentence is correct
     * @return if the answer was correct
     */
    public Response<Boolean> answerTrueLieQuestion(Auth auth, int roomId,String mail, boolean trueAnswer){
        throw new NotImplementedException();
    }


    /**
     * req3.6.2.5 - answer story mission
     * @param auth - authentication object
     * @param roomId - room id
     * @param sentence - the next sentence to add to the story
     * @return - the whole story after adding the next sentence
     */
    public Response<String> answerStoryMission(Auth auth,int roomId, String sentence){
        throw new NotImplementedException();
    }


    /**
     * req 3.6.2.6 - answer trivia mission( american question)
     * @param auth - authentication object
     * @param roomId - room id
     * @param answerNumber - the number of the answer that the students answers
     * @return - the correct answer number
     */
    public Response<Integer> answerTriviaQuestion(Auth auth,int roomId, int answerNumber){
        throw new NotImplementedException();
    }

    /**
     * TODO 3.6.2.7 talk about when the system draw the in charge
     */


    /**
     * req 3.7 - add suggestion
     * @param auth - authentication object
     * @param suggestion - suggestion to a mission the student want to send
     * @return if the suggestion was added successfully
     */
    public Response<Boolean> addSuggestion(Auth auth, String suggestion){
        throw new NotImplementedException();
    }

    /**
     * req 3.8 - reset password
     * @param auth - authentication object
     * @return if an email was sent with verification code
     */
    public Response<Boolean> resetPassword(Auth auth){
        throw new NotImplementedException();
    }

















}
