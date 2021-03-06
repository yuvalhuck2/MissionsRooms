package missions.room.Managers;

import CrudRepositories.*;
import DataAPI.*;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Communications.Publisher.Publisher;
import missions.room.Communications.Publisher.SinglePublisher;
import missions.room.Domain.*;
import missions.room.Domain.Notifications.NonPersistenceNotification;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Domain.Users.Student;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.missions.StoryMission;
import missions.room.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static Utils.Utils.checkString;

@Service
@CommonsLog
public class ManagerRoomStudent extends StudentManager {

    private static final String STORY_MISSION_NAME = "Story_Mission";

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private ClassGroupRepo classGroupRepo;

    @Autowired
    private OpenAnswerRepo openAnswerRepo;

    private static Publisher publisher;


    public ManagerRoomStudent() {
        super();
    }

    public static void initPublisher(){
        publisher= SinglePublisher.getInstance();
    }

    public ManagerRoomStudent(Ram ram, StudentCrudRepository studentCrudRepository, RoomCrudRepository roomCrudRepository, ClassroomRepository classroomRepository
                              ,GroupRepository groupRepository) {
        super(ram, studentCrudRepository);
        this.roomRepo = new RoomRepo(roomCrudRepository);
        this.classroomRepo = new ClassroomRepo(classroomRepository);
        this.classGroupRepo=new ClassGroupRepo(groupRepository);
        publisher=SinglePublisher.getInstance();
    }


    /**
     * req 3.6.2.1 - answer open question mission
     * @param apiKey
     * @param openAnswerData
     * @param file
     * @return if the answer was accepted successfully
     */
    //TODO real time notifications to move the other clients a room
    public Response<Boolean> answerOpenQuestionMission(String apiKey, SolutionData openAnswerData, MultipartFile file){
        OpCode validity = checkStudentIsMissionManager(apiKey, openAnswerData.getRoomId());
        if (validity == OpCode.Success) {
            try {
                byte[] fileBytes = file.getBytes();
                OpenAnswer openAnswer = new OpenAnswer(openAnswerData.getRoomId(), openAnswerData.getMissionId()
                                            , fileBytes, openAnswerData.getOpenAnswer());
                Room room = ram.getRoom(openAnswerData.getRoomId());
                if (room != null) {
                    synchronized (room) {
                        updateRoomAndMissionInCharge(room);
                    }
                }
                return openAnswerRepo.saveOpenAnswer(openAnswer);
            } catch (Exception ex) {
                return new Response<>(false, OpCode.FAILED_READ_FILE_BYTES);
            }
        } else {
            return new Response<>(false, validity);
        }

    }


    /**
     * req 3.6.2.3 - answer deterministic question mission
     * @param apiKey - authentication object
     * @param roomId - room id
     * @param answer - answer for the question
     * @return if the answer was correct
     */

    //TODO check that the apiKry is of mission in charge
    @Transactional
    public Response<Boolean> answerDeterministicQuestion(String apiKey, String roomId, boolean answer){
        Response<Student> checkStudent = checkStudent(apiKey);
        if (checkStudent.getReason() != OpCode.Success) {
            return new Response<>(null, checkStudent.getReason());
        }

        Response<Room> roomResponse=getRoomById(roomId);
        if (roomResponse.getReason() != OpCode.Success) {
            return new Response<>(null, roomResponse.getReason());
        }
        Room room = roomResponse.getValue();
        NonPersistenceNotification<RoomDetailsData> notification;
        //lock the room that is updated
        synchronized (room) {

            if (answer) {
                Response<Boolean> response = updateCorrectAnswer(room);
                if (response.getReason() != OpCode.Success) {
                    return response;
                }
            }

            OpCode reason;
            String nextInCharge=null;

            if (room.isLastMission()) {
                reason = roomRepo.deleteRoom(room).getReason();
                if (reason != OpCode.Success) {
                    return new Response<>(null, reason);
                }
                ram.deleteRoom(roomId);
                notification = new NonPersistenceNotification<>(OpCode.Finish_Missions_In_Room, null);
            } else {
                room.increaseCurrentMission();
                reason = roomRepo.save(room).getReason();
                if (reason != OpCode.Success) {
                    return new Response<>(null, reason);
                }
                //roll mission in charge
                notification = new NonPersistenceNotification<>(OpCode.Update_Room, room.getData());
                nextInCharge=room.drawMissionInCharge();
            }

            Set<String> userKeys=room.getConnectedUsersAliases();
            for (String alias :
                    userKeys) {
                publisher.update(ram.getApiKey(alias),notification);
            }
            //send to the next mission inCharge notification
            if(nextInCharge!=null){
                NonPersistenceNotification<String> inChargeNotification=new NonPersistenceNotification<>(OpCode.IN_CHARGE,roomId);
                publisher.update(ram.getApiKey(nextInCharge),inChargeNotification);
            }
        }

            return new Response<>(true, OpCode.Success);

    }


    private void updateRoomAndMissionInCharge(Room room) {
        OpCode reason;
        String nextInCharge=null;
        NonPersistenceNotification<RoomDetailsData> notification;
        if (room.isLastMission()) {
            reason = roomRepo.deleteRoom(room).getReason();
            if (reason != OpCode.Success) {
                log.error(String.format("Failed to delete room: {0}. function: updateRoomAndMissionInCharge", room.getRoomId()));
            }
            ram.deleteRoom(room.getRoomId());
            notification = new NonPersistenceNotification<>(OpCode.Finish_Missions_In_Room, null);
        } else {
            room.increaseCurrentMission();
            reason = roomRepo.save(room).getReason();
            if (reason != OpCode.Success) {
                log.error(String.format("Failed to save room: {0}. function: updateRoomAndMissionInCharge", room.getRoomId()));
            }
            //roll mission in charge
            notification = new NonPersistenceNotification<>(OpCode.Update_Room, room.getData());
            nextInCharge=room.drawMissionInCharge();
        }

        Set<String> userKeys=room.getConnectedUsersAliases();
        for (String alias :
                userKeys) {
            publisher.update(ram.getApiKey(alias),notification);
        }
        //send to the next mission inCharge notification
        if(nextInCharge!=null){
            NonPersistenceNotification<String> inChargeNotification=new NonPersistenceNotification<>(OpCode.IN_CHARGE,room.getRoomId());
            publisher.update(ram.getApiKey(nextInCharge),inChargeNotification);
        }
    }

    private Response<Boolean> updateCorrectAnswer(Room room) {
        RoomType roomType=room.updatePoints();
        OpCode reason = null;
        switch (roomType){
            case Class:
                Classroom classroom=((ClassroomRoom) room).getParticipant();
                reason=classroomRepo.save(classroom)
                        .getReason();
                break;
            case Group:
                ClassGroup classGroup = ((GroupRoom) room).getParticipant();
                reason = classGroupRepo.save(classGroup)
                        .getReason();
                break;
            case Personal:
                Student student=((StudentRoom) room).getParticipant();
                reason=studentRepo.save(student)
                        .getReason();
                break;
            default:
                //TODO logger error that can't happened
                break;

        }
        if(reason!=OpCode.Success){
            return new Response<>(null,reason);
        }
        return new Response<>(true,OpCode.Success);
    }

    /**
     * req 3.6.1 - watch data of a specific room
     * @param apiKey - authentication object
     * @return the mission details of the given room
     */
    public Response<RoomDetailsData> watchRoomData(String apiKey, String roomId) {
        Response<Student> checkStudent = checkStudent(apiKey);
        if (checkStudent.getReason() != OpCode.Success) {
            return new Response<>(null, checkStudent.getReason());
        }

        Response<Room> roomResponse = getRoomById(roomId);
        if(roomResponse.getReason()!=OpCode.Success){
            return new Response<>(null,roomResponse.getReason());
        }

        Student student=checkStudent.getValue();
        Room room=roomResponse.getValue();
        if(!room.isBelongToRoom(student.getAlias())){
            return new Response<>(null,OpCode.NOT_BELONGS_TO_ROOM);
        }

        OpCode opCode=ram.connectToRoom(roomId,student.getAlias());
        return new Response<>(room.getData(),opCode);
    }

    /**
     * disconnect from room
     * @param apiKey - authentication object
     * if the student was mission in charge, then send notification to the next mission in charge.
     * delete the room if there are no users connected.
     */
    public void disconnectFromRoom(String apiKey, String roomId) {
        Response<Student> checkStudent = checkStudent(apiKey);
        if (checkStudent.getReason() == OpCode.Success) {
            Student student = checkStudent.getValue();
            String inCharge=ram.disconnectFromRoom(roomId,student.getAlias());
            if(inCharge!=null){
                publisher.update(ram.getApiKey(inCharge),new NonPersistenceNotification<>(OpCode.IN_CHARGE,roomId));
            }
        }
    }

    /**
     * disconnect from all of my rooms
     * @param apiKey - authentication object
     */
    public void disconnectFromAllRooms(String apiKey) {
        Response<List<RoomDetailsData>> roomDetailsListResponse = watchRoomDetails(apiKey);
        if(roomDetailsListResponse.getReason()==OpCode.Success){
            for(RoomDetailsData roomDetailsData:roomDetailsListResponse.getValue()){
                disconnectFromRoom(apiKey,roomDetailsData.getRoomId());
            }
        }
    }

    /**
     * req 3.6.1 - watch details of the room
     *
     * @param apiKey - authentication object
     * @return the mission details of the given room
     * TODO refactor the name to watchMyRooms from the controller to the manager
     */
    public Response<List<RoomDetailsData>> watchRoomDetails(String apiKey) {
        Response<Student> checkStudent = checkStudent(apiKey);
        if (checkStudent.getReason() != OpCode.Success) {
            return new Response(null, checkStudent.getReason());
        }
        Student student = checkStudent.getValue();
        Response<Classroom> classroomResponse = classroomRepo.findClassroomByStudent(student.getAlias());
        if (classroomResponse.getReason() != OpCode.Success) {
            return new Response<>(null, classroomResponse.getReason());
        }
        Classroom classroom = classroomResponse.getValue();

        if (classroom == null){
            return new Response<>(null, OpCode.Student_Not_Exist_In_Class);
        }

        ClassGroup classGroup = classroom.getGroup(student.getAlias());
        if (classGroup == null) {
            return new Response<>(null, OpCode.Student_Not_Exist_In_Group);
        }

        Response<ClassroomRoom> responseClass = roomRepo.findClassroomRoomByAlias(classroom.getClassName());
        Response<GroupRoom> responseGroup =roomRepo.findGroupRoomByAlias(classGroup.getGroupName());
        Response<StudentRoom> responseStudent = roomRepo.findStudentRoomByAlias(student.getAlias());
        if (responseClass.getReason() == OpCode.DB_Error || responseGroup.getReason() == OpCode.DB_Error || responseStudent.getReason() == OpCode.DB_Error) {
            return new Response<>(null, OpCode.DB_Error);
        }
        List<Room> rooms = new ArrayList<>();

        if (responseClass.getReason() == OpCode.Success&&responseClass.getValue()!=null) {
            Response<Room> responseRoomClass = getRoomsFromRoomClass(responseClass.getValue());
            if (responseRoomClass.getReason() != OpCode.Success) {
                return new Response<>(null, responseRoomClass.getReason());
            }
            rooms.add(responseRoomClass.getValue());
        }

        if (responseGroup.getReason() == OpCode.Success&&responseGroup.getValue()!=null) {
            Response<Room> responseRoomGroup = getRoomsFromRoomGroup(responseGroup.getValue());
            if (responseRoomGroup.getReason() != OpCode.Success) {
                return new Response<>(null, responseGroup.getReason());
            }
            rooms.add(responseRoomGroup.getValue());
        }

        if (responseStudent.getReason() == OpCode.Success&&responseStudent.getValue()!=null) {
            Response<Room> responseRoomStudent = getRoomsFromRoomStudent(responseStudent.getValue());
            if (responseRoomStudent.getReason() != OpCode.Success) {
                return new Response<>(null, responseRoomStudent.getReason());
            }
            rooms.add(responseRoomStudent.getValue());
        }

        if (rooms.size()==0){
            return new Response<>(new ArrayList<>(),OpCode.Success);
        }
        return getRoomDetailFromRoom(rooms);
    }

    private Response<Room> getRoomsFromRoomClass(ClassroomRoom classroomRoom) {
        String roomId = classroomRoom.getRoomId();
        Response<Room> response = getRoomById(roomId);
        if (response.getReason() != OpCode.Success) {
            return new Response<>(null, response.getReason());
        }
        return new Response<>(response.getValue(), OpCode.Success);
    }

    private Response<Room> getRoomsFromRoomGroup(GroupRoom groupRoom) {
        String roomId = groupRoom.getRoomId();
        Response<Room> response = getRoomById(roomId);
        if (response.getReason() != OpCode.Success) {
            return new Response<>(null, response.getReason());
        }
        return new Response<>(response.getValue(), OpCode.Success);
    }

    private Response<Room> getRoomsFromRoomStudent(StudentRoom studentRoom) {
        String roomId = studentRoom.getRoomId();
        Response<Room> response = getRoomById(roomId);
        if (response.getReason() != OpCode.Success) {
            return new Response<>(null, response.getReason());
        }
        return new Response<>(response.getValue(), OpCode.Success);
    }

    private Response<Room> getRoomById(String roomId) {
        Room room = ram.getRoom(roomId);
        if (room==null) {
            Response<Room> response = roomRepo.findRoomById(roomId);
            if (response.getReason() == OpCode.Success) {
                if (response.getValue() == null) {
                    return new Response<>(null, OpCode.Not_Exist_Room);
                }
                ram.addRoom(response.getValue());
                room = ram.getRoom(roomId);
            } else {
                return response;
            }
        }
        return new Response<>(room, OpCode.Success);
    }

    private Response<List<RoomDetailsData>> getRoomDetailFromRoom(List<Room> rooms) {
        List<RoomDetailsData> roomDetailsDataList = new ArrayList<>();
        for (Room room : rooms) {
            RoomDetailsData roomDetailsData= room.getData();
            if(roomDetailsData!=null) {
                roomDetailsDataList.add(roomDetailsData);
            }
        }
        return new Response<>(roomDetailsDataList,OpCode.Success);
    }

    private OpCode checkStudentIsMissionManager(String apiKey, String roomId){
        String missionManager = ram.getMissionManager(roomId);
        String userAlias = ram.getAlias(apiKey);
        if (missionManager != null && userAlias != null ) {
            if (missionManager.equals(userAlias)) {
                return OpCode.Success;
            }
            return OpCode.STUDENT_NOT_IN_CHARGE;
        }
        return OpCode.INVALID_ROOM_ID;
    }

    /**
     * req3.6.2.4 - answer story mission
     * @param apiKey - authentication object
     * @param roomId - room id
     * @param sentence - the next sentence to add to the story
     * @return - the whole story after adding the next sentence
     */
    public Response<Boolean> answerStoryMission(String apiKey,String roomId, String sentence){
        if(!checkString(sentence)){
            return new Response<>(null,OpCode.Wrong_Sentence);
        }

        Response<Room> checkStoryResponse=checkStoryMissionValidity(apiKey, roomId);
        OpCode reason=checkStoryResponse.getReason();
        if(reason!=OpCode.Success){
            return new Response<>(null,reason);
        }
        Room room=checkStoryResponse.getValue();

        synchronized (room) {
            if(!room.isEnoughConnected()){
                return new Response<>(null,OpCode.Not_Enough_Connected);
            }
            String story = ((StoryMission) room.getCurrentMission()).updateStory(sentence);
            String nextInCharge = room.drawMissionInChargeForStory();
            if(nextInCharge==null){
                NonPersistenceNotification<String> storyNotification=new NonPersistenceNotification<>(OpCode.STORY_FINISH,
                        roomId,
                        story);
                Set<String> userKeys=room.getConnectedUsersAliases();
                for (String alias :
                        userKeys) {
                    publisher.update(ram.getApiKey(alias),storyNotification);
                }
            }
            else{
                NonPersistenceNotification<String> inChargeNotification=new NonPersistenceNotification<>(OpCode.STORY_IN_CHARGE,
                        roomId,
                        story);
                publisher.update(ram.getApiKey(nextInCharge),inChargeNotification);
            }
            return new Response<>(true,OpCode.Success);
        }

    }

    /**
     * req3.6.2.4 - answer story mission
     * @param apiKey - authentication object
     * @param roomId - room id
     * @return
     */
    public Response<Boolean> finishStoryMission(String apiKey, String roomId){
        Response<Room> checkStoryResponse=checkStoryMissionValidity(apiKey, roomId);
        OpCode reason=checkStoryResponse.getReason();
        Room room=checkStoryResponse.getValue();
        if(reason==OpCode.Success){
            synchronized (room) {
                if(room.clearStoryMission()) {
                    updateCorrectAnswer(room);
                    updateRoomAndMissionInCharge(room);
                }
                return new Response<>(true,OpCode.Success);
            }
        }
        return new Response<>(false,reason);
    }

    private Response<Room> checkStoryMissionValidity(String apiKey,String roomId){
        Response<Student> checkStudent=checkStudent(apiKey);
        if(checkStudent.getReason()!=OpCode.Success){
            return new Response<>(null,checkStudent.getReason());
        }

        Response<Room> roomResponse = getRoomById(roomId);
        if(roomResponse.getReason()!=OpCode.Success){
            return new Response<>(null,roomResponse.getReason());
        }

        Student student=checkStudent.getValue();
        Room room=roomResponse.getValue();
        if(!room.isBelongToRoom(student.getAlias())){
            return new Response<>(null,OpCode.NOT_BELONGS_TO_ROOM);
        }

        Mission mission=room.getCurrentMission();
        MissionData storyMission = getRoomCurrentStoryMission(mission);
        if(storyMission == null){
            return new Response<>(null,OpCode.Not_Story);
        }
        return new Response<>(room,OpCode.Success);
    }

    private MissionData getRoomCurrentStoryMission(Mission mission) {
        MissionData storyMission=mission.getData();
        if(storyMission.getName().equals(STORY_MISSION_NAME)){
            return storyMission;
        }
        return null;
    }

}
