package missions.room.Managers;

import DataAPI.*;
import ExternalSystems.HashSystem;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Communications.Publisher.Publisher;
import missions.room.Communications.Publisher.SinglePublisher;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.Classroom;
import missions.room.Domain.Notifications.NonPersistenceNotification;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Domain.Users.*;
import missions.room.Repo.*;
import Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class ITManager {

    protected static final Object ADD_USER_LOCK = new Object();

    @Autowired
    protected ITRepo itRepo;

    @Autowired
    protected UserRepo userRepo;

    @Autowired
    protected SchoolUserRepo schoolUserRepo;

    @Autowired
    protected ClassroomRepo classroomRepo;

    @Autowired
    protected RoomRepo roomRepo;

    private Ram ram;

    private HashSystem hashSystem;

    private static Publisher publisher;

    public ITManager() {
        this.ram = new Ram();
        hashSystem=new HashSystem();
    }

    public static void initPublisher(){
        publisher= SinglePublisher.getInstance();
    }

    protected Response<IT> checkIT(String apiKey){
        String alias = ram.getAlias(apiKey);
        if(alias==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        Response<IT> ITResponse = itRepo.findITById(alias);
        return checkITResponse(ITResponse);
    }

    private Response<IT> checkITResponse(Response<IT> ITResponse){
        if(ITResponse.getReason()!= OpCode.Success){
            log.error("connection to db lost");
            return new Response<>(null,ITResponse.getReason());
        }
        IT it = ITResponse.getValue();
        if(it == null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(it,OpCode.Success);
    }

    public Response<Boolean> deleteSeniorStudents(String apiKey){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }
        Response<List<Classroom>> responseClassroom=classroomRepo.findAll();
        if(responseClassroom.getReason()!=OpCode.Success){
            return new Response<>(false,responseClassroom.getReason());
        }
        for(Classroom classroom:responseClassroom.getValue()){
            if(classroom.getClassName().startsWith("2")){
                for(ClassGroup group:classroom.getClassGroups()){
                    for(String student:group.getStudent().keySet()){
                        deleteUser(apiKey,student);
                    }
                }
            }
        }
        return new Response<>(true,OpCode.Success);
    }

    @Transactional
    public Response<Boolean> deleteUser(String apiKey,String alias){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        Response<User> userResponse=userRepo.findUserForWrite(alias);

        if(userResponse.getReason()!=OpCode.Success){
            return new Response<>(false,userResponse.getReason());
        }
        User user=userResponse.getValue();
        synchronized (user){
            if(user instanceof Teacher){
                if(((Teacher)user).getClassroom()!=null){
                    return new Response<>(false,OpCode.Teacher_Has_Classroom);
                }
            }
            else if(user instanceof Student){
                Response<List<Room>> responseStudentRooms=getStudentRooms((Student)user);
                if(responseStudentRooms.getReason()!=OpCode.Success){
                    return new Response<>(false,responseStudentRooms.getReason());
                }
                for(Room room:responseStudentRooms.getValue()){
                    if(room instanceof StudentRoom){
                        roomRepo.deleteRoom(room);
                        ram.deleteRoom(room.getRoomId());
                    }
                    else{
                        String inCharge=ram.disconnectFromRoom(room.getRoomId(),user.getAlias());
                        if(inCharge!=null){
                            publisher.update(ram.getApiKey(inCharge),new NonPersistenceNotification<String>(OpCode.IN_CHARGE,room.getRoomId()));
                        }
                    }

                }

                Response<Classroom> classroomResponse=classroomRepo.findClassroomByStudent(user.getAlias());
                classroomResponse.getValue().deleteStudent(user.getAlias());
                classroomRepo.save(classroomResponse.getValue());

            }
        }
        if(ram.getApiKey(user.getAlias())!=null) {
            publisher.update(ram.getApiKey(user.getAlias()), new NonPersistenceNotification<String>(OpCode.DELETE_USER, ""));
        }
        return userRepo.delete(user);
    }

    private Response<List<Room>> getStudentRooms(Student student){
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
        return new Response<>(rooms,OpCode.Success);
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
    /**
     * req 6.4 - adding new IT to the system
     * @param apiKey - authentication object
     * @param newUser - new IT details
     * @return if user added successfully
     */
    public Response<Boolean> addNewIT(String apiKey, RegisterDetailsData newUser){
        String alias=newUser.getAlias();
        String password=newUser.getPassword();
        if(!Utils.checkString(alias)){
            log.info("add IT with empty or null alias");
            return new Response<>(false,OpCode.Wrong_Alias);
        }
        if(!Utils.checkString(password)){
            log.info("add IT with empty or null password");
            return new Response<>(false,OpCode.Wrong_Password);
        }
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }
        return saveITInDB(alias, password,itResponse.getValue().getAlias());
    }

    private Response<Boolean> saveITInDB( String alias, String password,String myAlias) {
        password= hashSystem.encrypt(password);
        Response<IT> itResponse;
        synchronized (ADD_USER_LOCK){
            Response<Boolean> userResponse = userRepo.isExistsById(alias);
            if(userResponse.getReason()!= OpCode.Success){
                log.error("connection to db lost");
                return new Response<>(false,userResponse.getReason());
            }
            if(userResponse.getValue()){
                log.info("there is user with that name already");
               return new Response<>(false,OpCode.Already_Exist);
            }
            itResponse= itRepo.save(new IT(alias,password));
        }

        if(itResponse.getReason()!=OpCode.Success){
            log.error("connection to db lost");
            return new Response<>(false,itResponse.getReason());
        }
        log.info(myAlias + " added " + alias + " to be new IT successfully");
        return new Response<>(true,OpCode.Success);
    }


    /**
     * req 6.5- update user details
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    @Transactional
    public Response<Boolean> updateUserDetails(String apiKey, UserProfileData profileDetails) {
        String firstName=profileDetails.getFirstName();
        String lastName=profileDetails.getLastName();
        boolean checkFName = Utils.checkString(firstName);
        boolean checkLName = Utils.checkString(lastName);
        if(!checkFName&&!checkLName){
            log.info("add IT with empty or null first name and last name");
            return new Response<>(false,OpCode.Wrong_Details);
        }
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        String alias=profileDetails.getAlias();
        Response<SchoolUser> schoolUserResponse = schoolUserRepo.findUserForWrite(alias);
        if(schoolUserResponse.getReason ()!= OpCode.Success){
            return new Response<>(false,schoolUserResponse.getReason());
        }
        SchoolUser schoolUser = schoolUserResponse.getValue();
        if(schoolUser == null){
            return new Response<>(false, OpCode.Not_Exist);
        }

        if(checkFName){
            schoolUser.setFirstName(firstName);
        }
        if(checkLName){
            schoolUser.setLastName(lastName);
        }

        OpCode reason = schoolUserRepo.save(schoolUser).getReason();
        return new Response<>(reason == OpCode.Success, reason);
    }

    public Response<List<UserProfileData>> getAllUsersSchoolProfiles() {
        Response<List<User>> users= schoolUserRepo.findAllSchoolUsers();
        if(users.getReason()!=OpCode.Success){
            log.error("Function getAllSchoolUsersProfiles: connection to the DB lost");
        }
        return new Response<>(users.getValue()
                .stream()
                .map((User::getProfileData))
                .collect(Collectors.toList()),
                OpCode.Success);
    }

    /**
     * req 6.9 - add user - student
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    @Transactional
    public Response<Boolean> addStudent(String apiKey, StudentData profileDetails) {
        Response<Boolean> checkDetails = checkStudentDetails(profileDetails);
        if(checkDetails.getReason()!=OpCode.Success){
            return checkDetails;
        }

        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        Response<Classroom> classroomResponse = classroomRepo.findForWrite(profileDetails.getClassroom());
        if(classroomResponse.getReason()!=OpCode.Success){
            return new Response<>(false, classroomResponse.getReason());
        }

        Classroom classroom = classroomResponse.getValue();

        synchronized (ADD_USER_LOCK){
            Response<Boolean> isUserExist = userRepo.isExistsById(profileDetails.getAlias());
            if(isUserExist.getReason()!=OpCode.Success){
                return new Response<>(false, isUserExist.getReason());
            }
            if(isUserExist.getValue()){
                return new Response<>(false,OpCode.Already_Exist);
            }

            classroom.addStudent(new Student(profileDetails), profileDetails.getGroupType());
            OpCode saveReason = classroomRepo.save(classroom).getReason();
            return new Response<>(saveReason==OpCode.Success,saveReason);
        }
    }

    /**
     * req 6.7 - close classroom
     * @param apiKey - authentication object
     * @param classroomName - tha identifier of the classroom need to close
     * @return if classroom closed successfully
     */
    @Transactional
    public Response<Boolean> closeClassroom(String apiKey, String classroomName) {
        log.info("close classroom "+ classroomName);
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        Response<Classroom> classroomResponse = classroomRepo.findForWrite(classroomName);
        if(classroomResponse.getReason()!=OpCode.Success){
            return new Response<>(false, classroomResponse.getReason());
        }

        Classroom classroom = classroomResponse.getValue();
        if(classroom.hasStudents()){
            log.info("classroom has students");
            return new Response<>(false, OpCode.Has_Students);
        }

        return classroomRepo.delete(classroom);
    }

    /**
     * req 6.9 - add user - teacher
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    public Response<Boolean> addTeacher(String apiKey, TeacherData profileDetails) {
        Response<Boolean> checkDetails = checkTeacherDetails(profileDetails);
        if(checkDetails.getReason()!=OpCode.Success){
            return checkDetails;
        }

        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        Teacher teacher;

        synchronized (ADD_USER_LOCK){
            Response<Boolean> isUserExist = userRepo.isExistsById(profileDetails.getAlias());
            if(isUserExist.getReason()!=OpCode.Success){
                return new Response<>(false, isUserExist.getReason());
            }
            if(isUserExist.getValue()){
                return new Response<>(false,OpCode.Already_Exist);
            }
            if(profileDetails.isSupervisor()){
                teacher = new Supervisor(profileDetails);
            }
            else{
                teacher = new Teacher(profileDetails);
            }
            OpCode saveReason = schoolUserRepo.save(teacher).getReason();
            return new Response<>(saveReason==OpCode.Success,saveReason);
        }
    }

    private Response<Boolean> checkStudentDetails(StudentData profileDetails) {
        if(!Utils.checkString(profileDetails.getClassroom())){
            return new Response<>(false,OpCode.Wrong_Classroom);
        }
        if(profileDetails.getGroupType()==null||profileDetails.getGroupType()==GroupType.BOTH){
            return new Response<>(false,OpCode.Wrong_Group);
        }
        return checkTeacherDetails(new TeacherData(profileDetails));
    }

    private Response<Boolean> checkTeacherDetails(TeacherData teacherData) {
        if(!Utils.checkString(teacherData.getAlias())){
            return new Response<>(false,OpCode.Wrong_Alias);
        }
        if(!Utils.checkString(teacherData.getFirstName())){
            return new Response<>(false,OpCode.Wrong_First_Name);
        }
        if(!Utils.checkString(teacherData.getLastName())){
            return new Response<>(false,OpCode.Wrong_Last_Name);
        }
        return new Response<>(true,OpCode.Success);
    }

}
