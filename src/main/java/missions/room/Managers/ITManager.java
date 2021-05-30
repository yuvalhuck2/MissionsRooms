package missions.room.Managers;

import DataAPI.ClassroomAndGroupsData;
import DataObjects.APIObjects.RegisterDetailsData;
import DataObjects.APIObjects.StudentData;
import DataObjects.APIObjects.TeacherData;
import DataObjects.FlatDataObjects.GroupType;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.UserProfileData;
import ExternalSystems.HashSystem;
import Utils.Utils;
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
    protected static final String SENIOR_PREFIX = "2";

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

    @Autowired
    protected TeacherRepo teacherRepo;

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

    public Response<Integer> deleteSeniorStudents(String apiKey){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(null,itResponse.getReason());
        }
        Response<List<Classroom>> responseClassroom=classroomRepo.findAll();
        if(responseClassroom.getReason()!=OpCode.Success){
            return new Response<>(null,responseClassroom.getReason());
        }
        int counter=0;
        for(Classroom classroom:responseClassroom.getValue()){
            if(classroom.getClassName().startsWith(SENIOR_PREFIX)){
                for(ClassGroup group:classroom.getClassGroups()){
                    List<String> students=new ArrayList<>(group.getStudent().keySet());
                    for(String student:students){
                        if(deleteUser(apiKey,student).getReason()==OpCode.Success){
                            counter++;
                        }
                    }
                }
            }
        }
        return new Response<>(counter,OpCode.Success);
    }

    @Transactional
    public Response<Boolean> deleteUser(String apiKey,String alias){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        Response<BaseUser> userResponse=userRepo.findUserForWrite(alias);

        if(userResponse.getReason()!=OpCode.Success){
            return new Response<>(false,userResponse.getReason());
        }
        BaseUser user=userResponse.getValue();
        if(user == null) {
            return new Response<>(false, OpCode.Not_Exist);
        }
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
                        publisher.update(ram.getApiKey(inCharge),new NonPersistenceNotification<>(OpCode.IN_CHARGE,room.getRoomId()));
                    }
                }

            }

            Response<Classroom> classroomResponse=classroomRepo.findClassroomByStudent(user.getAlias());
            classroomResponse.getValue().deleteStudent(user.getAlias());
            classroomRepo.save(classroomResponse.getValue());

        }

        if(ram.getApiKey(user.getAlias())!=null) {
            publisher.update(ram.getApiKey(user.getAlias()), new NonPersistenceNotification<>(OpCode.DELETE_USER, ""));
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
        Response<List<BaseUser>> users= schoolUserRepo.findAllSchoolUsers();
        if(users.getReason()!=OpCode.Success){
            log.error("Function getAllSchoolUsersProfiles: connection to the DB lost");
        }
        return new Response<>(users.getValue()
                .stream()
                .map((BaseUser::getProfileData))
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
        if(profileDetails.getGroupType()==null||profileDetails.getGroupType()== GroupType.BOTH){
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

    @Transactional
    public Response<Boolean> transferTeacherClassroom(String apiKey,String alias,String classroomName,GroupType groupType){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }
        Response<BaseUser> responseTeacher=userRepo.findUserForWrite(alias);
        if(responseTeacher.getReason()!=OpCode.Success){
            return new Response<>(false,responseTeacher.getReason());
        }
        Teacher teacher=(Teacher)responseTeacher.getValue();
        if(teacher.getClassroom()!=null){
            return new Response<>(false,OpCode.Teacher_Has_Classroom);
        }

        Response<List<Teacher>> prevTeacherResponse=teacherRepo.findTeacherForWriteByClassroom(classroomName);
        if(prevTeacherResponse.getReason()!=OpCode.Success){
            return new Response<>(false,prevTeacherResponse.getReason());
        }

        Response<Classroom> classroomResponse=classroomRepo.find(classroomName);
        if(classroomResponse.getReason()!=OpCode.Success){
            return new Response<>(false,classroomResponse.getReason());
        }
        int numOfGroups=classroomResponse.getValue().getClassGroups().size();
        List<Teacher> teachers=prevTeacherResponse.getValue();
        Teacher prevTeacherA=null;
        Teacher prevTeacherB=null;
        Teacher prevTeacherBoth=null;


        for(Teacher t:teachers){
            if(t.getGroupType().equals(GroupType.A)){
                prevTeacherA=t;
            }
            else if(t.getGroupType().equals(GroupType.B)){
                prevTeacherB=t;
            }
            else if(t.getGroupType().equals(GroupType.BOTH)){
                prevTeacherBoth=t;
            }
        }
        boolean transferAlsoClassroom=false;

        switch(groupType.name()){
            case "A":
                if(prevTeacherA!=null){
                    transferAlsoClassroom=numOfGroups==1;
                    prevTeacherA.setClassroom(null);
                    prevTeacherA.setGroupType(GroupType.C);
                    teacherRepo.save(prevTeacherA);
                    transferByTeacher(prevTeacherA,teacher,transferAlsoClassroom);
                }
                else if(prevTeacherBoth!=null){
                    prevTeacherBoth.setGroupType(GroupType.B);
                    teacherRepo.save(prevTeacherBoth);
                    transferByTeacher(prevTeacherBoth,teacher,false);
                }
                break;
            case "B":
                if(prevTeacherB!=null){
                    transferAlsoClassroom=numOfGroups==1;
                    prevTeacherB.setClassroom(null);
                    prevTeacherB.setGroupType(GroupType.C);
                    teacherRepo.save(prevTeacherB);
                    transferByTeacher(prevTeacherB,teacher,transferAlsoClassroom);
                }
                else if(prevTeacherBoth!=null){
                    prevTeacherBoth.setGroupType(GroupType.A);
                    teacherRepo.save(prevTeacherBoth);
                    transferByTeacher(prevTeacherBoth,teacher,false);
                }
                break;
            case "BOTH":
                transferAlsoClassroom=true;
                if(prevTeacherBoth!=null){
                    prevTeacherBoth.setClassroom(null);
                    prevTeacherBoth.setGroupType(GroupType.C);
                    teacherRepo.save(prevTeacherBoth);
                    transferByTeacher(prevTeacherBoth,teacher,transferAlsoClassroom);
                }
                else{
                    prevTeacherA.setClassroom(null);
                    prevTeacherA.setGroupType(GroupType.C);
                    prevTeacherB.setClassroom(null);
                    prevTeacherB.setGroupType(GroupType.C);
                    teacherRepo.save(prevTeacherA);
                    teacherRepo.save(prevTeacherB);
                    transferByTeacher(prevTeacherA,teacher,transferAlsoClassroom);
                    transferByTeacher(prevTeacherB,teacher,transferAlsoClassroom);
                }
                break;
        }


        synchronized (teacher){
            teacher.setClassroom(classroomResponse.getValue());
            teacher.setGroupType(groupType);
            teacherRepo.save(teacher);
            /*
            Response<Iterable<Room>> responseRoom=roomRepo.findRoomsForWriteByTeacherAlias(prevTeacher.getAlias());
            if(responseRoom.getReason()!=OpCode.Success){
                return new Response<>(false,responseRoom.getReason());
            }
            for(Room room:responseRoom.getValue()){
                synchronized (room){
                    if(ram.isRoomExist(room.getRoomId())){
                        ram.getRoom(room.getRoomId()).setTeacher(teacher);
                    }
                    else{
                        room.setTeacher(teacher);
                        roomRepo.save(room);
                    }
                }
            }*/
        }
        return new Response<>(true,OpCode.Success);
    }

    private Response<Boolean> transferByTeacher(Teacher prevTeacher,Teacher currTeacher,boolean transferAlsoClassroom){
        Response<Iterable<Room>> responseRoom=roomRepo.findRoomsForWriteByTeacherAlias(prevTeacher.getAlias());
        if(responseRoom.getReason()!=OpCode.Success){
            return new Response<>(false,responseRoom.getReason());
        }
        for(Room room:responseRoom.getValue()){
            if(room instanceof ClassroomRoom && !transferAlsoClassroom){
                continue;
            }
            synchronized (room){
                if(ram.isRoomExist(room.getRoomId())){
                    ram.getRoom(room.getRoomId()).setTeacher(currTeacher);
                }
                else{
                    room.setTeacher(currTeacher);
                    roomRepo.save(room);
                }
            }
        }
        return new Response<>(true,OpCode.Success);
    }




    public Response<List<ClassroomAndGroupsData>> getAllClassrooms(String apiKey){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(null,itResponse.getReason());
        }
        Response<List<Classroom>> response=classroomRepo.findAll();
        if(response.getReason()!=OpCode.Success){
            return new Response<>(null,response.getReason());
        }
        List<ClassroomAndGroupsData> res=new ArrayList<>();
        for(Classroom classroom:response.getValue()){
            List<GroupType> groups=new ArrayList<>();
            for(ClassGroup classGroup:classroom.getClassGroups()){
                groups.add(classGroup.getGroupType());
            }
            if(groups.size()>1){
                groups.add(GroupType.BOTH);
            }
            res.add(new ClassroomAndGroupsData(classroom.getClassName(),classroom.getClassHebrewName(),groups));
        }
        if(res.size()==0)
            return new Response<>(res,OpCode.Empty);
        return new Response<>(res,OpCode.Success);
    }

    public Response<List<ClassroomAndGroupsData>> getAllClassroomsByGrade(String apiKey,String alias){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(null,itResponse.getReason());
        }

        Response<SchoolUser> studentResponse=schoolUserRepo.findUserForRead(alias);
        if(studentResponse.getReason()!=OpCode.Success){
            return new Response<>(null,studentResponse.getReason());
        }
        Student student=(Student)studentResponse.getValue();

        Response<Classroom> studentClassroomResponse=classroomRepo.findClassroomByStudent(student.getAlias());
        if(studentClassroomResponse.getReason()!=OpCode.Success){
            return new Response<>(null,studentClassroomResponse.getReason());
        }
        Classroom studentClassroom=studentClassroomResponse.getValue();

        String grade=studentClassroom.getClassName().substring(0,1);
        Response<List<Classroom>> classroomsResponse=classroomRepo.findClassroomByGrade(grade);
        if(classroomsResponse.getReason()!=OpCode.Success){
            return new Response<>(null,classroomsResponse.getReason());
        }
        List<ClassroomAndGroupsData> res=new ArrayList<>();
        for(Classroom classroom:classroomsResponse.getValue()){
            List<GroupType> groups=new ArrayList<>();
            for(ClassGroup classGroup:classroom.getClassGroups()){
                if(!classGroup.containsStudent(alias)){
                    groups.add(classGroup.getGroupType());
                }
            }
            if(groups.size()>0)
                res.add(new ClassroomAndGroupsData(classroom.getClassName(),classroom.getClassHebrewName(),groups));
        }
        if(res.size()==0)
            return new Response<>(res,OpCode.Empty);
        return new Response<>(res,OpCode.Success);

    }

    @Transactional
    public Response<Boolean> transferStudentClassroom(String apiKey,String alias,String classroomName,GroupType groupType){
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success) {
            log.error(itResponse.getReason().toString());
            return new Response<>(false, itResponse.getReason());
        }
        Response<Classroom> studentClassroomResponse=classroomRepo.findClassroomByStudent(alias);
        if(studentClassroomResponse.getReason()!=OpCode.Success){
            return new Response<>(false,studentClassroomResponse.getReason());
        }
        Classroom studentClassroom=studentClassroomResponse.getValue();
        synchronized (studentClassroom) {
            studentClassroom.deleteStudent(alias);
            classroomRepo.save(studentClassroom);
        }

        Response<SchoolUser> studentResponse=schoolUserRepo.findUserForRead(alias);
        if(studentResponse.getReason()!=OpCode.Success){
            return new Response<>(false,studentResponse.getReason());
        }

        Response<Classroom> newClassroomResponse=classroomRepo.findForWrite(classroomName);
        if(newClassroomResponse.getReason()!=OpCode.Success){
            return new Response<>(false,newClassroomResponse.getReason());
        }
        Classroom newClassroom=newClassroomResponse.getValue();
        synchronized (newClassroom){
            if(!newClassroom.addStudent(studentResponse.getValue(),groupType)){
                return new Response<>(false,OpCode.Wrong_Group);
            }
            classroomRepo.save(newClassroom);
        }
        return new Response<>(true,OpCode.Success);





    }

}
