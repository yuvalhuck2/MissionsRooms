package missions.room.Service;

import org.springframework.stereotype.Service;

@Service
public class ServiceAPI {

////    @Autowired
////    private LogicManager logicManager;
////
////    /**
////     * req 2.2 - register
////     * @param details - user details
////     * @return if mail with the code was sent successfully
////     */
////    public Response<Boolean> register (RegisterDetailsData details){
////        return logicManager.register(details);
////    }
////
////
////    /**
////     * req 2.2 - register code
////     * * @param alias - user alias
////     * @param code - user details
////     * @return if register succeeded
////     */
////    public Response<Boolean> registerCode (String alias, String code){
////        return logicManager.registerCode(alias,code);
////    }
//
//
//
//
////    /**
////     * req 2.3 -login
////     * @param alias - user alias
////     * @param password - user password
////     * @return API key if login succeeded
////     */
////    public Response<String> login (String alias, String password){
////        throw new RuntimeException();
////    }
//
//    /**
//     * req 3.2
//     * TODO talk about delete profile
//     */
//
//    /**
//     * req 3.6.1 - watch details of the room
//     * @param auth - authentication object
//     * @param roomId - room id
//     * @return the mission details of the given room
//     * TODO check on database if can generate unique string
//     */
//    public Response<RoomDetailsData> watchRoomDetails (Auth auth, String roomId){
//        throw new RuntimeException();
//    }
//
//    /**
//     * req 3.6.2.1 - answer open question mission
//     * @param auth - authentication object
//     * @param roomId - room id
//     * @param answer - verbal answer of the open question
//     * @param file - file attached to the solution
//     * @return if the answer was accepted successfully
//     */
//    //TODO real time notifications to move the other clients a room
//    public Response<Boolean> answerOpenQuestionMission(Auth auth, String roomId,String answer,String file){
//        throw new RuntimeException();
//    }
//
//
//    //TODO talk about discussion mission
//    /**
//     * req 3.6.2.2 - answer open discussion mission
//     * @return if the answer was accepted successfully
//     */
//
//
//    /**
//     * req 3.6.2.3 - answer deterministic question mission
//     * @param auth - authentication object
//     * @param roomId - room id
//     * @param answer - answer for the question
//     * @return if the answer was correct
//     */
//
//    public Response<Boolean> answerDeterministicQuestion(Auth auth,String roomId,String answer){
//        throw new RuntimeException();
//    }
//
//
//
//    /**
//     * req 3.6.2.4.1 - fill true and lie sentences
//     * @param auth - authentication object
//     * @param roomId - room id
//     * @param trueSentence - the correct sentence about the student
//     * @param falseSentence - incorrect sentence about the student
//     * @return if the sentences were added to the mission
//     */
//    public Response<Boolean> fillTrueLieSentences(Auth auth, String roomId, String trueSentence, String falseSentence){
//        throw new RuntimeException();
//    }
//
//
//    /**
//     * req 3.6.2.4.2 - answer true lie sentence
//     * @param auth - authentication object
//     * @param roomId - room id
//     * @param otherAlias - the alias of the other student
//     * @param trueAnswer - true if the sentence the student think the first sentence is correct
//     * @return if the answer was correct
//     */
//    public Response<Boolean> answerTrueLieMission(Auth auth, String roomId, String otherAlias, int trueAnswer){
//        throw new RuntimeException();
//    }
//
//
//    /**
//     * req3.6.2.5 - answer story mission
//     * @param auth - authentication object
//     * @param roomId - room id
//     * @param sentence - the next sentence to add to the story
//     * @return - the whole story after adding the next sentence
//     */
//    public Response<String> answerStoryMission(Auth auth,String roomId, String sentence){
//        throw new RuntimeException();
//    }
//
//
//    //TODO change to all answers in one request and return all the answers.
//    /**
//     * req 3.6.2.6 - answer trivia mission( american question)
//     * @param auth - authentication object
//     * @param roomId - room id
//     * @param answerNumber - the number of the answer that the students answers
//     * @return - the correct answer number
//     */
//    public Response<Integer> answerTriviaQuestion(Auth auth,String roomId, int answerNumber){
//        throw new RuntimeException();
//    }
//
//    /**
//     * TODO 3.6.2.7 talk about when the system draw the in charge
//     */
//
//
//    /**
//     * req 3.9 - send room message
//     * @param auth - authentication object
//     * @param message - message to send to the other user
//     * @param mail - target user mail
//     * @param roomId - the room to send a message to
//     * @return if the message was sent successfully
//     */
//    public Response<Boolean> sendMessage(Auth auth,String message,String mail,String roomId){
//        throw new RuntimeException();
//    }
//
////    /**
////     * req 4.2 - close missions room
////     * @param auth - authentication object
////     * @param roomId - the identifier of the room
////     * @return if the room was closed successfully
////     */
////    public Response<Boolean> closeRoom(Auth auth, String roomId){
////        throw new RuntimeException();
////    }
//
//
////    /**
////     * req 4.3 - search missions
////     * @param auth - authentication object
////     * @param filter - details about how to filter the missions
////     * @return - list of the missions were filtered
////     */
////    public Response<List<MissionData>> searchMissions(Auth auth, MissionFilterData filter){
////        throw new RuntimeException();
////    }
//
//
////    /**
////     * req 4.4 - add room template
////     * @param auth - authentication object
////     * @param details - details of the template to be created
////     * @return if the template was added successfully
////     */
////    public Response<Boolean> createRoomTemplate(Auth auth, RoomTemplateDetailsData details){
////        throw new RuntimeException();
////    }
//
//
////    /**
////     * req 4.5 - add mission
////     * @param auth - authentication object
////     * @param missionData - details of the mission
////     * @return if the mission was added successfully
////     */
////    public Response<Boolean> createMission(Auth auth, MissionData missionData){
////        throw new RuntimeException();
////    }
//
//
////    /**
////     * req 4.6 - create trivia subject
////     * @param auth - authentication object
////     * @param subject - the subject of the trivia the teacher want to add
////     * @return if the subject was added successfully
////     */
////    public Response<Boolean> createTriviaSubject(Auth auth, String subject){
////        throw new RuntimeException();
////    }
////
////
////    /**
////     * req 4.7 - add trivia question
////     * @param auth - authentication object
////     * @param question - question details
////     * @return if the question was added successfully
////     */
////    public Response<Boolean> addTriviaQuestion(Auth auth, TriviaQuestionData question){
////        throw new RuntimeException();
////    }
//
//
//    /**
//     * req 4.8 - delete trivia question
//     * @param auth - authentication object
//     * @param questionID - the ID of the question
//     * @return if the question was deleted successfully
//     */
//    public Response<Boolean> deleteTriviaQuestion(Auth auth, String questionID){
//        throw new RuntimeException();
//    }
//
//    /**
//     * req 4.9 - watch students solutions
//     * @param auth - authentication object
//     * @return all the solutions that wait to be approved
//     */
//    public Response<List<SolutionData>> watchSolutions(Auth auth){
//        throw new RuntimeException();
//    }
//
//    /**
//     * req 4.10 - approve or deny student's solution
//     * @param auth - authentication object
//     * @param roomId - the room identifier
//     * @param missionId - the mission identifier
//     * @param isApproved - if the solution was approved
//     * @return if the mission was approved successfully
//     */
//    public Response<Boolean> responseStudentSolution(Auth auth,String roomId, String missionId,boolean isApproved){
//        throw new RuntimeException();
//    }
//
//
//
////    /**
////     * req 4.14 - look for room templates
////     * @param auth - authentication object
////     * @param filter - details about how to filter the rooms
////     * @return list of filtered room templates
////     */
////    public Response<List<RoomTemplateDetailsData>> searchRoomTemplates(Auth auth, TemplateFilerData filter){
////        throw new RuntimeException();
////    }
//
//    /**
//     * req 6.1 - transfer student group
//    * @param auth - authentication object
//     * @param alias - the identifier of the student that needs to change his ערםופ.
//     * @param newGroup - new classGroup
//     * @return if classroom changed successfully
//     */
//    public Response<Boolean> changeStudentClassroom(Auth auth,String alias, String newGroup){
//        throw new RuntimeException();
//    }
//
//    /**
//     * req 6.2 - delete user from the system
//     * @param auth- authentication object
//     * @param userMail - the identifier of the user that needs to delete.
//     * @return if user deleted successfully
//     */
//    public Response<Boolean> deleteUser(Auth auth,String userMail){
//        throw new RuntimeException();
//    }
//
//
//    /**
//     * req 6.5.1 - update user mail
//     * @param auth - authentication object
//     * @param userMail - the identifier of the user that needs to update his mail.
//     * @param newMail - new mail to update
//     * @return if mail updated successfully
//     */
//    public Response<Boolean> UpdateUserMail(Auth auth,String userMail,String newMail){
//        throw new RuntimeException();
//    }
//
//    /**
//     * req 6.5.2 - update user first name
//     * @param auth - authentication object
//     * @param userMail - the identifier of the user that needs to update his first name.
//     * @param FirstName - first name to update
//     * @return if first name updated successfully
//     */
//    public Response<Boolean> UpdateUserFirstName(Auth auth,String userMail,String FirstName){
//        throw new RuntimeException();
//    }
//
//    /**
//     * req 6.5.3 - update user last name
//     * @param auth - authentication object
//     * @param userMail - the identifier of the user that needs to update his last name.
//     * @param LastName - last name to update
//     * @return if last name updated successfully
//     */
//    public Response<Boolean> UpdateUserLastName(Auth auth,String userMail,String LastName){
//        throw new RuntimeException();
//    }
//
//    /**
//     * req 6.5.4 - update user phone number
//     * @param auth - authentication object
//     * @param userMail - the identifier of the user that needs to update his phone number.
//     * @param PhoneNumber - phone number t update
//     * @return if phone number updated successfully
//     */
//    public Response<Boolean> UpdateUserPhoneNumber(Auth auth,String userMail,String PhoneNumber){
//        throw new RuntimeException();
//    }
//
//
//    /**
//     * req 6.6 - delete all students that graduated from school
//     * @param auth - authentication object
//     * @return if users deleted successfully
//     */
//    public Response<Boolean> DeleteAllLastGradeStudents(Auth auth){
//        throw new RuntimeException();
//    }
//
//
//
//    /**
//     * req 6.8 - transfer teacher classroom
//     * @param auth - authentication object
//     * @param teacherMail - the identifier of the teacher that needs to change his classroom.
//     * @param newClassroom - new classroom
//     * @return if classroom changed successfully
//     */
//    public Response<Boolean> changeTeacherClassroom(Auth auth,String teacherMail, String newClassroom){
//        throw new RuntimeException();
//    }
//
//























}
