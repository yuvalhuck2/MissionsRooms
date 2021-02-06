package missions.room.Managers;

import CrudRepositories.RoomCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.*;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import missions.room.Domain.*;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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


//    public Response<ClassRoomData> getClassRoomData(String apiKey){
//        Response<Teacher> checkTeacher=checkTeacher(apiKey);
//        if(checkTeacher.getReason()!=OpCode.Success){
//            return new Response<>(null,checkTeacher.getReason());
//        }
//        Response<Teacher> response=teacherRepo.findTeacherById(checkTeacher.getValue().getAlias());
//        if(response.getReason()!=OpCode.Success){
//            return new Response<>(null,response.getReason());
//        }
//        Teacher teacher=response.getValue();
//        Classroom classroom=teacher.getClassroom();
//        GroupType groupType=teacher.getGroupType();
//
//
//        Map<GroupType,GroupData> groupDataMap=new HashMap<>();
//        for(ClassGroup cg:classroom.getClassGroups()){
//
//            GroupData groupData=new GroupData(cg.getGroupName(),cg.getGroupType());
//            List<StudentData> students=new ArrayList<>();
//            for(Student student:cg.getStudent().values()){
//                StudentData studentData=new StudentData(student.getAlias(),student.getFirstName(),student.getLastName());
//                students.add(studentData);
//            }
//            groupData.setStudents(students);
//            groupDataMap.put(cg.getGroupType(),groupData);
//        }
//        List<GroupData> groups=new ArrayList<>();
//        switch (groupType){
//            case A:
//                groups.add(groupDataMap.get(GroupType.A));break;
//            case B:
//                groups.add(groupDataMap.get(GroupType.B));break;
//            case BOTH:
//                groups.add(groupDataMap.get(GroupType.A));
//                groups.add(groupDataMap.get(GroupType.B));break;
//        }
//
//        ClassRoomData classRoomData=new ClassRoomData(classroom.getClassName(),groups);
//        return new Response<>(classRoomData,OpCode.Success);
//
//    }

}
