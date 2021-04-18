package missions.room.Managers;

import CrudRepositories.RoomCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.*;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
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
import missions.room.Domain.Users.Teacher;
import missions.room.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@CommonsLog
public class RoomManager extends TeacherManager {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private RoomTemplateRepo roomTemplateRepo;



    public RoomManager() {
        super();
    }

    public RoomManager(Ram ram, TeacherCrudRepository teacherCrudRepository, RoomCrudRepository roomCrudRepository, RoomTemplateCrudRepository roomTemplateCrudRepository) {
        super(ram, teacherCrudRepository);
        this.roomRepo = new RoomRepo(roomCrudRepository);
        this.roomTemplateRepo=new RoomTemplateRepo(roomTemplateCrudRepository);
    }

    /**
     * req 4.1 -create room
     * @param apiKey -  authentication object
     * @param roomDetails - the room details
     * @return if the room was added
     */
    public Response<Boolean> createRoom(String apiKey, NewRoomDetails roomDetails){
        Response<Teacher> checkTeacher=checkTeacher(apiKey);
        if(checkTeacher.getReason()!=OpCode.Success){
            return new Response<>(false,checkTeacher.getReason());
        }
        Response<Room> roomResponse= validateDetails(roomDetails,checkTeacher.getValue());
        return new Response<>(roomResponse.getReason()==OpCode.Success,roomResponse.getReason());
    }

    private Response<Room> saveRoom(Room room) {
        Response<Room> roomResponse=roomRepo.save(room);
        if(roomResponse.getReason()!= OpCode.Success){
            return new Response<>(null,roomResponse.getReason());
        }

        return new Response<>(room,OpCode.Success);
    }

    private Response<Room> validateDetails(NewRoomDetails roomDetails, Teacher teacher) {
        if(roomDetails==null){
            return new Response<>(null,OpCode.Null_Error);
        }
        if(!Utils.checkString(roomDetails.getRoomName())){
            return new Response<>(null,OpCode.Wrong_Name);
        }
        if(roomDetails.getBonus()<0){
            return new Response<>(null,OpCode.Wrong_Bonus);
        }
        if(teacher.getClassroom()==null){
            return new Response<>(null,OpCode.Supervisor);
        }
        roomDetails.setRoomId(UniqueStringGenerator.getTimeNameCode("room"));
        return saveRoomByType(roomDetails,teacher);
    }

    @Transactional
    protected Response<Room> saveRoomByType(NewRoomDetails roomDetails, Teacher teacher) {
        Response<RoomTemplate> roomTemplateResponse=roomTemplateRepo.findRoomTemplateById(roomDetails.getRoomTemplateId());
        if(roomTemplateResponse.getReason()!=OpCode.Success){
            return new Response<>(null,roomTemplateResponse.getReason());
        }
        RoomTemplate roomTemplate=roomTemplateResponse.getValue();
        if(roomTemplate==null){
            return new Response<>(null,OpCode.Not_Exist_Template);
        }
        if(roomTemplate.getType()!=roomDetails.getRoomType()){
            return new Response<>(null,OpCode.Type_Not_Match);
        }
        switch(roomDetails.getRoomType()){
            case Personal:
                return getStudentRoomResponse(roomDetails,roomTemplate,teacher);
            case Group:
                return getGroupRoomResponse(roomDetails,roomTemplate,teacher);
            case Class:
                return getClassroomRoomResponse(roomDetails,roomTemplate,teacher);
            default:
                return new Response<>(null,OpCode.Wrong_Type);
        }
    }

    @Transactional
    protected Response<Room> getClassroomRoomResponse(NewRoomDetails roomDetails, RoomTemplate roomTemplate, Teacher teacher) {
        Classroom classroom=teacher.getClassroom();
        if(classroom==null || !classroom.getClassName().equals(roomDetails.getParticipantKey())){//real teacher
            return new Response<>(null, OpCode.Not_Exist_Classroom);
        }
        Response<ClassroomRoom> classroomResponse=roomRepo.findClassroomRoomForWriteByAlias(roomDetails.getParticipantKey());
        if(classroomResponse.getValue()!=null){//if the group is already in a room personal type
            return new Response<>(null,OpCode.Already_Exist_Class);
        }
        if(classroomResponse.getReason()!=OpCode.Success){//check if there is db error or lock error
            return new Response<>(null,classroomResponse.getReason());
        }
        return saveRoom(new ClassroomRoom(roomDetails.getRoomId(),roomDetails.getRoomName(),classroom,teacher,roomTemplate,roomDetails.getBonus()));
    }

    @Transactional
    protected Response<Room> getGroupRoomResponse(NewRoomDetails roomDetails, RoomTemplate roomTemplate, Teacher teacher) {
        ClassGroup group=teacher.getGroupByName(roomDetails.getParticipantKey());
        if(group==null){
            return new Response<>(null, OpCode.Not_Exist_Group);
        }
        Response<GroupRoom> groupRoomResponse=roomRepo.findGroupRoomForWriteByAlias(group.getGroupName());//all real
        if(groupRoomResponse.getValue()!=null){//if the group is already in a room group type
            return new Response<>(null,OpCode.Already_Exist_Group);
        }
        if(groupRoomResponse.getReason()!=OpCode.Success){//check if there is db error or lock error
            return new Response<>(null,groupRoomResponse.getReason());
        }
        return saveRoom(new GroupRoom(roomDetails.getRoomId(),roomDetails.getRoomName(),group,teacher,roomTemplate,roomDetails.getBonus()));
    }

    @Transactional
    protected Response<Room> getStudentRoomResponse(NewRoomDetails roomDetails, RoomTemplate roomTemplate, Teacher teacher) {
        Student student=teacher.getStudent(roomDetails.getParticipantKey());
        if(student==null){
            return new Response<>(null, OpCode.Not_Exist_Student);
        }
        Response<StudentRoom> studentRoomResponse=roomRepo.findStudentRoomForWriteByAlias(student.getAlias());
        if(studentRoomResponse.getValue()!=null){//if the student is already in a room personal type
            return new Response<>(null,OpCode.Already_Exist_Student);
        }
        if(studentRoomResponse.getReason()!=OpCode.Success){//check if there is db error or lock error
            return new Response<>(null,studentRoomResponse.getReason());
        }
        return saveRoom(new StudentRoom(roomDetails.getRoomId(),roomDetails.getRoomName(),student,teacher,roomTemplate,roomDetails.getBonus()));
    }

    /**
     * req 4.2 - close missions room
     * @param apiKey - authentication object
     * @param roomId - the identifier of the room
     * @return if the room was closed successfully
     */
    
    public Response<Boolean> closeRoom(String apiKey, String roomId){
        Response<Teacher> checkTeacher=checkTeacher(apiKey);
        if(checkTeacher.getReason()!=OpCode.Success){
            return new Response<>(false,checkTeacher.getReason());
        }
        Response<Room> roomResponse=roomRepo.findRoomById(roomId);
        if(roomResponse.getReason()!=OpCode.Success){
            return new Response<>(false,roomResponse.getReason());
        }
        Room room=roomResponse.getValue();
        //TODO don't close room if someone is connected to it
        return roomRepo.deleteRoom(room);
    }

    public Response<ClassRoomData> getClassRoomData(String apiKey){
        Response<Teacher> checkTeacher=checkTeacher(apiKey);
        if(checkTeacher.getReason()!=OpCode.Success){
            return new Response<>(null,checkTeacher.getReason());
        }
        Teacher teacher=checkTeacher.getValue();
        Classroom classroom=teacher.getClassroom();
        if(classroom==null){//means that this is a supervisor
            return new Response<>(null,OpCode.Supervisor);
        }
        return new Response<>(classroom.getClassroomData(teacher.getGroupType()),OpCode.Success);
    }

    /**
     * req 4.10 - approve or deny student's solution
     * @param roomId - the room identifier
     * @param missionId - the mission identifier
     * @return appropriate message to send all participants
     */
    public Response<String> responseStudentSolution(String apiKey, String roomId, String missionId, boolean isApproved) {
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(null,teacherResponse.getReason());
        }
        Response<Room> roomRes = getRoomById(roomId);
        if(roomRes.getReason() != OpCode.Success) {
            return new Response<>(null, roomRes.getReason());
        }
        Room room = roomRes.getValue();
        boolean isMissionResolved;
        if (room.isMissionExists(missionId)) {
            synchronized (room) {
                isMissionResolved = room.resolveMission(missionId);
                if (isMissionResolved) {
                    Response<Boolean> updateResponse = updateRoom(room);
                    if (!updateResponse.getValue()) {
                        return new Response<>(null, updateResponse.getReason());
                    }
                    String message = getResponseToStudentSolutionMessage(isApproved, updateResponse.getReason());
                    message = String.format(message, room.getName());
                    return new Response<>(message, OpCode.Success);
                } else { // no open ans in mission id
                    return new Response<>(null, OpCode.MISSION_NOT_IN_OPEN_ANS);
                }
            }
        } else { // mission id not in room
            return new Response<>(null, OpCode.MISSION_NOT_IN_ROOM);
        }

    }

    private OpCode getResponseCode(boolean isApproved, OpCode reason) {
        OpCode code;
        if (isApproved) {
            if(reason == OpCode.ROOM_CLOSED) {
                code = OpCode.APPROVED_CLOSE;
            } else {
                code = OpCode.APPROVED_OPEN;
            }
        } else {
            if(reason == OpCode.ROOM_CLOSED) {
                code = OpCode.REJECT_CLOSE;
            } else {
                code = OpCode.REJECT_OPEN;
            }
        }
        return code;
    }

    /**
     * get all the student aliases belongs to room.
     * IMPORTANT: does not do api check, relies on previous checks.
     * @param roomId
     * @return
     */
    public Response<Set<String>> getAllRoomParticipants(String roomId) {
        Response<Room> roomRes = getRoomById(roomId);
        if(roomRes.getReason() != OpCode.Success) {
            return new Response<>(null, roomRes.getReason());
        }
        Room room = roomRes.getValue();
        return new Response<>(room.getStudentsAlias(), OpCode.Success);
    }

    public Response<String> getRoomName( String roomId) {
        Response<Room> roomResponse = getRoomById(roomId);
        if(roomResponse.getReason() != OpCode.Success) {
            return new Response<>(null, roomResponse.getReason());
        }
        return new Response<>(roomResponse.getValue().getName(), OpCode.Success);
    }

    private Response<Boolean> updateRoom(Room room) {
        Response<Boolean> response;
        if(room.allOpenQuestionsApproved()) {
            response = roomRepo.deleteRoom(room);
            response.setReason(response.getValue() ?
                    OpCode.ROOM_CLOSED :
                    response.getReason());
        } else {
            Response<Room> roomRes = saveRoom(room);
            response = new Response<>(roomRes.getValue() != null,
                    roomRes.getReason() == OpCode.Success ? OpCode.ROOM_SAVED : roomRes.getReason());
        }
        return response;
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

    private String getResponseToStudentSolutionMessage(boolean isApproved, OpCode roomUpdateCode) {
        String message;
        if (isApproved) {
            if(roomUpdateCode == OpCode.ROOM_CLOSED) {
                message = OpenAnswerResponseMessages.approveCloseMessage;
            } else {
                message = OpenAnswerResponseMessages.approveOpenMessage;
            }
        } else {
            if(roomUpdateCode == OpCode.ROOM_CLOSED) {
                message = OpenAnswerResponseMessages.rejectCloseMessage;
            } else {
                message = OpenAnswerResponseMessages.rejectOpenMessage;
            }
        }
        return message;
    }

}
