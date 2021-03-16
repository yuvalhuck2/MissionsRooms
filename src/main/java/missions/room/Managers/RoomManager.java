package missions.room.Managers;

import CrudRepositories.RoomCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.*;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import lombok.extern.apachecommons.CommonsLog;
import javafx.util.Pair;
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

import javax.xml.transform.sax.TemplatesHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CommonsLog
public class RoomManager extends TeacherManager {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private RoomTemplateRepo roomTemplateRepo;

    private static Publisher publisher;

    public static void initPublisher(){
        publisher= SinglePublisher.getInstance();
    }

    public RoomManager() {
        super();
    }

    public RoomManager(Ram ram, TeacherCrudRepository teacherCrudRepository, RoomCrudRepository roomCrudRepository, RoomTemplateCrudRepository roomTemplateCrudRepository) {
        super(ram, teacherCrudRepository);
        this.roomRepo = new RoomRepo(roomCrudRepository);
        this.roomTemplateRepo=new RoomTemplateRepo(roomTemplateCrudRepository);

        publisher=SinglePublisher.getInstance();

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
     * disconnect from room
     * @param apiKey - authentication object
     * if the student was mission in charge, then send notification to the next mission in charge.
     * delete the room if there are no users connected.
     */
    public void disconnectFromRoom(String apiKey, String roomId) {
        Response<Teacher> checkTeacher = checkTeacher(apiKey);
        if (checkTeacher.getReason() == OpCode.Success) {
            Teacher teacher = checkTeacher.getValue();
            String inCharge=ram.disconnectFromRoom(roomId,teacher.getAlias());
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
        Response<List<RoomsDataByRoomType>> roomDetailsListResponse = watchMyClassroomRooms(apiKey);
        if(roomDetailsListResponse.getReason()==OpCode.Success){
            for(RoomsDataByRoomType roomDetailsData:roomDetailsListResponse.getValue()){
                for(RoomDetailsData rdd:roomDetailsData.getRoomDetailsDataList()) {
                    disconnectFromRoom(apiKey, rdd.getRoomId());
                }
            }
        }
    }

    /**
     * req 3.6.1 - watch data of a specific room
     * @param apiKey - authentication object
     * @return the mission details of the given room
     */
    public Response<Boolean> watchSpecificRoom(String apiKey, String roomId) {
        Response<Teacher> checkTeacher = checkTeacher(apiKey);
        if (checkTeacher.getReason() != OpCode.Success) {
            return new Response<>(false, checkTeacher.getReason());
        }

        Response<Room> roomResponse = getRoomById(roomId);
        if(roomResponse.getReason()!=OpCode.Success){
            return new Response<>(false,roomResponse.getReason());
        }

        Teacher teacher=checkTeacher.getValue();
        OpCode opCode=ram.connectToRoom(roomId,teacher.getAlias());
        if(opCode==OpCode.Teacher){
            return new Response<>(true,OpCode.Success);
        }
        return new Response<>(false,opCode);
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
     *  watch details of the room
     *
     * @param apiKey - authentication object
     * @return the mission details of the given room
     */
    public Response<List<RoomsDataByRoomType>> watchMyClassroomRooms(String apiKey){
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if (teacherResponse.getReason() != OpCode.Success) {
            return new Response(null, teacherResponse.getReason());
        }
        Teacher teacher = teacherResponse.getValue();
        Classroom classroom = teacher.getClassroom();

        if (classroom == null){
            return new Response<>(null, OpCode.Teacher_Classroom_Is_Null);
        }
        Response<ClassroomRoom> responseClass = roomRepo.findClassroomRoomByAlias(classroom.getClassName());
        List<RoomDetailsData>  classroomResponse;
        if (responseClass.getReason() == OpCode.Success&&responseClass.getValue()!=null) {
            Response<Room> responseRoomClass = getRoomsFromRoomClass(responseClass.getValue());
            if (responseRoomClass.getReason() != OpCode.Success) {
                return new Response<>(null, responseRoomClass.getReason());
            }
            List<Room> temp=new ArrayList<>();
            temp.add(responseRoomClass.getValue());
            classroomResponse=getRoomDetailFromRoom(temp).getValue();
        }
        else  if (responseClass.getReason() == OpCode.Success&&responseClass.getValue()==null) {
            classroomResponse=new ArrayList<>();
        }
        else return new Response<>(null,responseClass.getReason());
        List<RoomDetailsData> classGroupResponse=getClassGroupRooms(teacher).getValue();
        List<RoomDetailsData> studentsResponse=getStudentsRoom(teacher).getValue();


        List<RoomsDataByRoomType> response=new ArrayList<>();
        response.add(new RoomsDataByRoomType(RoomType.Class,classroomResponse));
        response.add(new RoomsDataByRoomType(RoomType.Group,classGroupResponse));
        response.add(new RoomsDataByRoomType(RoomType.Personal,studentsResponse));


        return new Response<>(response,OpCode.Success);
    }

    private Response<List<RoomDetailsData>> getClassGroupRooms(Teacher teacher){
        Pair<ClassGroup,ClassGroup> classGroups=getClassroomGroups(teacher.getClassroom());
        ClassGroup a=classGroups.getKey(); ClassGroup b=classGroups.getValue();
        List<Room> groupRooms=new ArrayList<>();

        Response<GroupRoom> responseGroup;
        switch (teacher.getGroupType().name()){
            case "A":
                if(a==null)
                    break;
                responseGroup =roomRepo.findGroupRoomByAlias(a.getGroupName());
                Response<Room> response=checkResponse(responseGroup);
                if(response.getReason()!=OpCode.Success){ return new Response<>(null,response.getReason()); }
                else if(response.getValue()!=null){ groupRooms.add(response.getValue()); }

            case "B":
                if(b==null)
                    break;
                responseGroup =roomRepo.findGroupRoomByAlias(b.getGroupName());
                response=checkResponse(responseGroup);
                if(response.getReason()!=OpCode.Success){ return new Response<>(null,response.getReason()); }
                else if(response.getValue()!=null){ groupRooms.add(response.getValue()); }
            case "BOTH":
                if(a!=null) {
                    responseGroup = roomRepo.findGroupRoomByAlias(a.getGroupName());
                    response = checkResponse(responseGroup);
                    if (response.getReason()!=OpCode.Success) {
                        return new Response<>(null, response.getReason());
                    } else if(response.getValue()!=null){
                        groupRooms.add(response.getValue());
                    }
                }
                if(b!=null){
                responseGroup =roomRepo.findGroupRoomByAlias(b.getGroupName());
                response=checkResponse(responseGroup);
                if(response.getReason()!=OpCode.Success){ return new Response<>(null,response.getReason()); }
                else if(response.getValue()!=null){ groupRooms.add(response.getValue()); }}
        }

        return getRoomDetailFromRoom(groupRooms);
    }

    private Response<List<RoomDetailsData>> getStudentsRoom(Teacher teacher) {
        Pair<ClassGroup,ClassGroup> classGroups=getClassroomGroups(teacher.getClassroom());
        ClassGroup a=classGroups.getKey(); ClassGroup b=classGroups.getValue();
        Set<String> students=new HashSet<>();
        List<Room> studentsRooms=new ArrayList<>();
        switch (teacher.getGroupType().name()) {
            case "A":
                if(a!=null) {
                    students.addAll(a.getStudentsAlias());
                }
            case "B":
                if(b!=null) {
                    students.addAll(b.getStudentsAlias());
                }
            case "BOTH":
                if(a!=null) {
                    students.addAll(a.getStudentsAlias());
                }
                if(b!=null) {
                    students.addAll(b.getStudentsAlias());
                }
        }

        for(String alias:students) {
            Response<StudentRoom> responseStudent = roomRepo.findStudentRoomByAlias(alias);
            if (responseStudent.getReason() == OpCode.Success&&responseStudent.getValue()!=null) {
                Response<Room> responseRoomStudent = getRoomsFromRoomStudent(responseStudent.getValue());
                if (responseRoomStudent.getReason() != OpCode.Success) {
                    return new Response<>(null, responseRoomStudent.getReason());
                }
                studentsRooms.add(responseRoomStudent.getValue());
            }
        }
        return getRoomDetailFromRoom(studentsRooms);
    }

    private Pair<ClassGroup,ClassGroup> getClassroomGroups(Classroom classroom){

        ClassGroup a=null;
        ClassGroup b=null;
        for(ClassGroup classGroup:classroom.getClassGroups()){
            if(classGroup.getGroupType()==GroupType.A){
                a=classGroup;
            }
            if(classGroup.getGroupType()==GroupType.B){
                b=classGroup;
            }
        }
        return new Pair<ClassGroup,ClassGroup>(a,b);
    }

    private Response<Room> checkResponse(Response<GroupRoom> response){
        if (response.getReason() == OpCode.Success&&response.getValue()!=null) {
            Response<Room> responseRoomGroup = getRoomsFromRoomGroup(response.getValue());
            if (responseRoomGroup.getReason() != OpCode.Success) {
                return new Response<>(null, response.getReason());
            }
            return new Response<>(responseRoomGroup.getValue(),OpCode.Success);
            //groupRooms.add(responseRoomGroup.getValue());
        }
        return new Response<>(null,response.getReason());
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


}
