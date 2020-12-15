package Service;

import DataAPI.*;
import org.springframework.stereotype.Service;
import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
public class ServiceAPI {

//    @Autowired
//    private LogicManager logicManager;
//
//    /**
//     * req 2.2 - register
//     * @param details - user details
//     * @return if mail with the code was sent successfully
//     */
//    public Response<Boolean> register (RegisterDetailsData details){
//        return logicManager.register(details);
//    }
//
//
//    /**
//     * req 2.2 - register code
//     * * @param alias - user alias
//     * @param code - user details
//     * @return if register succeeded
//     */
//    public Response<Boolean> registerCode (String alias, String code){
//        return logicManager.registerCode(alias,code);
//    }




//    /**
//     * req 2.3 -login
//     * @param alias - user alias
//     * @param password - user password
//     * @return API key if login succeeded
//     */
//    public Response<String> login (String alias, String password){
//        throw new NotImplementedException();
//    }


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
    public Response<RoomDetailsData> watchRoomDetails (Auth auth, String roomId){
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
    public Response<Boolean> answerOpenQuestionMission(Auth auth, String roomId,String answer,String file){
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
    public Response<Boolean> answerDeterministicQuestion(Auth auth,String roomId,String answer){
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
    public Response<Boolean> answerTrueLieQuestion(Auth auth, String roomId,String trueSentence, String falseSentence){
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
    public Response<Boolean> answerTrueLieQuestion(Auth auth, String roomId,String mail, boolean trueAnswer){
        throw new NotImplementedException();
    }


    /**
     * req3.6.2.5 - answer story mission
     * @param auth - authentication object
     * @param roomId - room id
     * @param sentence - the next sentence to add to the story
     * @return - the whole story after adding the next sentence
     */
    public Response<String> answerStoryMission(Auth auth,String roomId, String sentence){
        throw new NotImplementedException();
    }


    //TODO change to all answers in one request and return all the answers.
    /**
     * req 3.6.2.6 - answer trivia mission( american question)
     * @param auth - authentication object
     * @param roomId - room id
     * @param answerNumber - the number of the answer that the students answers
     * @return - the correct answer number
     */
    public Response<Integer> answerTriviaQuestion(Auth auth,String roomId, int answerNumber){
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

    /**
     * req 3.9 - send room message
     * @param auth - authentication object
     * @param message - message to send to the other user
     * @param mail - target user mail
     * @param roomId - the room to send a message to
     * @return if the message was sent successfully
     */
    public Response<Boolean> sendMessage(Auth auth,String message,String mail,String roomId){
        throw new NotImplementedException();
    }

    /**
     * req 4.1 - create room
     */

    /**
     * req 4.2 - close missions room
     * @param auth - authentication object
     * @param roomId - the identifier of the room
     * @return if the room was closed successfully
     */
    public Response<Boolean> closeRoom(Auth auth, String roomId){
        throw new NotImplementedException();
    }


    /**
     * req 4.3 - search missions
     * @param auth - authentication object
     * @param filter - details about how to filter the missions
     * @return - list of the missions were filtered
     */
    public Response<List<MissionData>> searchMissions(Auth auth, MissionFilterData filter){
        throw new NotImplementedException();
    }


    /**
     * req 4.4 - add room template
     * @param auth - authentication object
     * @param details - details of the template to be created
     * @return if the template was added successfully
     */
    public Response<Boolean> createRoomTemplate(Auth auth, RoomTemplateDetailsData details){
        throw new NotImplementedException();
    }


//    /**
//     * req 4.5 - add mission
//     * @param auth - authentication object
//     * @param missionData - details of the mission
//     * @return if the mission was added successfully
//     */
//    public Response<Boolean> createMission(Auth auth, MissionData missionData){
//        throw new NotImplementedException();
//    }


    /**
     * req 4.6 - create trivia subject
     * @param auth - authentication object
     * @param subject - the subject of the trivia the teacher want to add
     * @return if the subject was added successfully
     */
    public Response<Boolean> createTriviaSubject(Auth auth, String subject){
        throw new NotImplementedException();
    }


    /**
     * req 4.7 - add trivia question
     * @param auth - authentication object
     * @param question - question details
     * @return if the question was added successfully
     */
    public Response<Boolean> addTriviaQuestion(Auth auth, TriviaQuestionData question){
        throw new NotImplementedException();
    }


    /**
     * req 4.8 - delete trivia question
     * @param auth - authentication object
     * @param questionID - the ID of the question
     * @return if the question was deleted successfully
     */
    public Response<Boolean> deleteTriviaQuestion(Auth auth, String questionID){
        throw new NotImplementedException();
    }

    /**
     * req 4.9 - watch students solutions
     * @param auth - authentication object
     * @return all the solutions that wait to be approved
     */
    public Response<List<SolutionData>> watchSolutions(Auth auth){
        throw new NotImplementedException();
    }

    /**
     * req 4.10 - approve or deny student's solution
     * @param auth - authentication object
     * @param roomId - the room identifier
     * @param missionId - the mission identifier
     * @param isApproved - if the solution was approved
     * @return if the mission was approved successfully
     */
    public Response<Boolean> responseStudentSolution(Auth auth,String roomId, String missionId,boolean isApproved){
        throw new NotImplementedException();
    }


    /**
     * req 4.11 - watch student's suggestions
     * @param auth - authentication object
     * @return the student's suggestions
     */
    public Response<List<String>> watchSuggestions(Auth auth){
        throw new NotImplementedException();
    }


    /**
     * req 4.12 - delete student's suggestion
     * @param auth - authentication object
     * @param suggestionId - identifier of the suggestion need to be deleted
     * @return if the suggestion was deleted successfully
     */
    public Response<Boolean> responseStudentSuggestion(Auth auth,String suggestionId){
        throw new NotImplementedException();
    }


    /**
     * req 4.13 - deduce points to a student
     * @param auth - authentication object
     * @param studentMail - the identifier of the student need to deduce points to.
     * @param pointsToDeduce - the amount of points to deduce the user
     * @return if the points were deducted successfully
     */
    public Response<Boolean> deducePoints(Auth auth,String studentMail, int pointsToDeduce){
        throw new NotImplementedException();
    }


    /**
     * req 4.14 - look for room templates
     * @param auth - authentication object
     * @param filter - details about how to filter the rooms
     * @return list of filtered room templates
     */
    public Response<List<RoomTemplateDetailsData>> searchRoomTemplates(Auth auth, TemplateFilerData filter){
        throw new NotImplementedException();
    }

    /**
     * req 6.1 - transfer student classroom
    * @param auth - authentication object
     * @param studentMail - the identifier of the student that needs to change his classroom.
     * @param newClassroom - new classroom
     * @return if classroom changed successfully
     */
    public Response<Boolean> changeStudentClassroom(Auth auth,String studentMail, String newClassroom){
        throw new NotImplementedException();
    }

    /**
     * req 6.2 - delete user from the system
     * @param auth- authentication object
     * @param userMail - the identifier of the user that needs to delete.
     * @return if user deleted successfully
     */
    public Response<Boolean> deleteUser(Auth auth,String userMail){
        throw new NotImplementedException();
    }


    /**
     * req 6.3 - upload csv file with users details
     * @param auth - authentication object
     * @param usersList -  list of users details to add/update in system
     * @return if users details updated in system
     */
    public Response<Boolean> UploadCSV(Auth auth,List<UserProfileData> usersList){
        throw new NotImplementedException();
    }

    /**
     * req 6.4 - adding new IT to the system
     * @param auth - authentication object
     * @param newUser - new IT details
     * @return if user added successfully
     */
    public Response<Boolean> addNewIT(Auth auth,UserProfileData newUser){
        throw new NotImplementedException();
    }

    /**
     * req 6.5.1 - update user mail
     * @param auth - authentication object
     * @param userMail - the identifier of the user that needs to update his mail.
     * @param newMail - new mail to update
     * @return if mail updated successfully
     */
    public Response<Boolean> UpdateUserMail(Auth auth,String userMail,String newMail){
        throw new NotImplementedException();
    }

    /**
     * req 6.5.2 - update user first name
     * @param auth - authentication object
     * @param userMail - the identifier of the user that needs to update his first name.
     * @param FirstName - first name to update
     * @return if first name updated successfully
     */
    public Response<Boolean> UpdateUserFirstName(Auth auth,String userMail,String FirstName){
        throw new NotImplementedException();
    }

    /**
     * req 6.5.3 - update user last name
     * @param auth - authentication object
     * @param userMail - the identifier of the user that needs to update his last name.
     * @param LastName - last name to update
     * @return if last name updated successfully
     */
    public Response<Boolean> UpdateUserLastName(Auth auth,String userMail,String LastName){
        throw new NotImplementedException();
    }

    /**
     * req 6.5.4 - update user phone number
     * @param auth - authentication object
     * @param userMail - the identifier of the user that needs to update his phone number.
     * @param PhoneNumber - phone number t update
     * @return if phone number updated successfully
     */
    public Response<Boolean> UpdateUserPhoneNumber(Auth auth,String userMail,String PhoneNumber){
        throw new NotImplementedException();
    }


    /**
     * req 6.6 - delete all students that graduated from school
     * @param auth - authentication object
     * @return if users deleted successfully
     */
    public Response<Boolean> DeleteAllLastGradeStudents(Auth auth){
        throw new NotImplementedException();
    }

    /**
     * req 6.7 - close classroom
     * @param auth - authentication object
     * @param classroom - tha identifier of the classroom need to close
     * @return if classroom closed successfully
     */
    public Response<Boolean> CloseClassroom(Auth auth,String classroom){
        throw new NotImplementedException();
    }

    /**
     * req 6.8 - transfer teacher classroom
     * @param auth - authentication object
     * @param teacherMail - the identifier of the teacher that needs to change his classroom.
     * @param newClassroom - new classroom
     * @return if classroom changed successfully
     */
    public Response<Boolean> changeTeacherClassroom(Auth auth,String teacherMail, String newClassroom){
        throw new NotImplementedException();
    }

























}
