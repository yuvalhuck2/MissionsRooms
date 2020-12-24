package Data;

import DataAPI.*;
import missions.room.Domain.*;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Domain.missions.KnownAnswerMission;
import DomainMocks.TeacherMock;

import java.util.*;

public class DataGenerator {

    private HashMap<Data, Student> students;
    private HashMap<Data,RegisterDetailsData> registerDetailsDatas;
    private HashMap<Data,String> verificationCodes;
    private HashMap<Data, Teacher> teachers;
    private HashMap<Data, Mission> missions;
    private HashMap<Data, RoomTemplate> roomTemplates;
    private HashMap<Data, RoomTemplateDetailsData> roomTemplatesDatas;
    private HashMap<Data, Room> roomsMap;
    private HashMap<Data,newRoomDetails> newRoomDetailsMap;
    private HashMap<Data,Classroom> classRoomMap;
    private HashMap<Data,ClassGroup> classGroupMap;

    public DataGenerator() {
        initStudents();
        initGroups();
        initClassrooms();
        initRegisterDetailsDatas();
        initVerificationCodes();
        initTeacher();
        initMissions();
        initRoomTemplateDatas();
        initRoomTemplates();
        initRooms();
        initNewRoomDetails();
    }

    private void initClassrooms() {
        classRoomMap=new HashMap<Data, Classroom>();
        Classroom valid=new Classroom("class",classGroupMap.get(Data.Empty_Students),classGroupMap.get(Data.Valid_Group));
        classRoomMap.put(Data.Valid_Classroom,valid);
    }

    private void initGroups() {
        classGroupMap=new HashMap<Data, ClassGroup>();
        HashMap<String,Student> studentHashMap=new HashMap<>();
        studentHashMap.put(students.get(Data.VALID).getAlias(),students.get(Data.VALID));
        classGroupMap.put(Data.Valid_Group,new ClassGroup("g2",studentHashMap));
        classGroupMap.put(Data.Empty_Students,new ClassGroup("g1",new HashMap<>()));
    }

    private void initNewRoomDetails() {
        newRoomDetailsMap =new HashMap<Data, newRoomDetails>();
        String student=students.get(Data.VALID).getAlias();
        String group=classGroupMap.get(Data.Valid_Group).getGroupName();
        String classroom =classRoomMap.get(Data.Valid_Classroom).getClassName();
        String teacher=teachers.get(Data.VALID_WITH_PASSWORD).getAlias();
        String roomTemplateStudent=roomTemplates.get(Data.VALID).getRoomTemplateId();
        String roomTemplateGroup=roomTemplates.get(Data.Valid_Group).getRoomTemplateId();
        String roomTemplateClassroom=roomTemplates.get(Data.Valid_Classroom).getRoomTemplateId();
        newRoomDetailsMap.put(Data.Valid_Student,new newRoomDetails(student,RoomType.Personal,"name",teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.Valid_Group,new newRoomDetails(group,RoomType.Group,"name",teacher,roomTemplateGroup,3));
        newRoomDetailsMap.put(Data.Valid_Classroom,new newRoomDetails(classroom,RoomType.Class,"name",teacher,roomTemplateClassroom,3));
        newRoomDetailsMap.put(Data.NULL_NAME,new newRoomDetails(student,RoomType.Personal,null,teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.EMPTY_NAME,new newRoomDetails(student,RoomType.Personal,"",teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.NULL,null);
        newRoomDetailsMap.put(Data.NEG_AMOUNT,new newRoomDetails(student,RoomType.Personal,"name",teacher,roomTemplateStudent,-1));
        newRoomDetailsMap.put(Data.TYPE_NOT_MATCH,new newRoomDetails(group,RoomType.Group,"name",teacher,roomTemplateStudent,3));
    }

    private void initRooms() {
        roomsMap=new HashMap<Data, Room>();
        Room studentRoom=new StudentRoom("roomId","name",students.get(Data.VALID),teachers.get(Data.VALID),roomTemplates.get(Data.VALID),4);
        roomsMap.put(Data.Valid_Student,studentRoom);
        roomsMap.put(Data.NULL_NAME,new StudentRoom("roomId",null,students.get(Data.VALID),teachers.get(Data.VALID),roomTemplates.get(Data.VALID),4));
        roomsMap.put(Data.EMPTY_NAME,new StudentRoom("roomId","",students.get(Data.VALID),teachers.get(Data.VALID),roomTemplates.get(Data.VALID),4));
    }

    private void initRoomTemplateDatas() {
        roomTemplatesDatas=new HashMap<Data, RoomTemplateDetailsData>();
        String missionId=getMission(Data.Valid_Deterministic).getMissionId();
        List<String> missions=new ArrayList<>();
        missions.add(missionId);
        roomTemplatesDatas.put(Data.VALID,new RoomTemplateDetailsData(missions,"name",1,RoomType.Personal));
        roomTemplatesDatas.put(Data.Valid_Group,new RoomTemplateDetailsData(missions,"name",1,RoomType.Group));
        roomTemplatesDatas.put(Data.Valid_Classroom,new RoomTemplateDetailsData(missions,"name",1,RoomType.Class));
        roomTemplatesDatas.put(Data.NULL_NAME,new RoomTemplateDetailsData(missions,null,1,RoomType.Personal));
        roomTemplatesDatas.put(Data.EMPTY_NAME,new RoomTemplateDetailsData(missions,"",1,RoomType.Personal));
        roomTemplatesDatas.put(Data.NEG_AMOUNT,new RoomTemplateDetailsData(missions,"name",-1,RoomType.Personal));
        roomTemplatesDatas.put(Data.BIG_AMOUNT,new RoomTemplateDetailsData(missions,"name",2,RoomType.Personal));
        roomTemplatesDatas.put(Data.NULL_TYPE,new RoomTemplateDetailsData(missions,"name",1,null));
        roomTemplatesDatas.put(Data.NULL_LIST,new RoomTemplateDetailsData(null,"name",1,RoomType.Personal));
        roomTemplatesDatas.put(Data.EMPTY_LIST,new RoomTemplateDetailsData(new ArrayList<>(),"name",0,RoomType.Personal));
        roomTemplatesDatas.put(Data.TYPE_NOT_MATCH,new RoomTemplateDetailsData(missions,"name",1,RoomType.Group));
        List<String> missionsWithNull=new ArrayList<>();
        missionsWithNull.add("not exist");
        roomTemplatesDatas.put(Data.WRONG_ID,new RoomTemplateDetailsData(missionsWithNull,"name",1,RoomType.Personal));
    }

    private void initRoomTemplates() {
        roomTemplates=new HashMap<Data, RoomTemplate>();
        RoomTemplateDetailsData templateDetailsData=getRoomTemplateData(Data.VALID);
        templateDetailsData.setId("rt");
        List<Mission> missionsMap=new ArrayList<>();
        Mission detMission=getMission(Data.Valid_Deterministic);
        missionsMap.add(detMission);

        List<Mission> missionsMapAllTypes=new ArrayList<>();
        missionsMapAllTypes.add(getMission(Data.Valid_Deterministic_All_Types));

        RoomTemplateDetailsData templateDetailsDataGroup=getRoomTemplateData(Data.Valid_Group);
        templateDetailsDataGroup.setId("group");

        RoomTemplateDetailsData templateDetailsDataClass=getRoomTemplateData(Data.Valid_Classroom);
        templateDetailsDataClass.setId("class");

        roomTemplates.put(Data.VALID,new RoomTemplate(templateDetailsData,missionsMap));
        roomTemplates.put(Data.Valid_Group,new RoomTemplate(templateDetailsDataGroup,missionsMapAllTypes));
        roomTemplates.put(Data.Valid_Classroom,new RoomTemplate(templateDetailsDataClass,missionsMapAllTypes));
    }

    private void initMissions() {
        missions=new HashMap<Data, Mission>();
        Set<RoomType> types=new HashSet<>();
        Set<RoomType> typesNull=new HashSet<>();
        Set<RoomType> allTypes=new HashSet<>();
        allTypes.add(RoomType.Personal);
        allTypes.add(RoomType.Group);
        allTypes.add(RoomType.Class);
        typesNull.add(null);
        types.add(RoomType.Personal);
        missions.put(Data.Valid_Deterministic,new KnownAnswerMission("ddd",types,"question","answer"));
        missions.put(Data.Valid_Deterministic_All_Types,new KnownAnswerMission("ddd",allTypes,"question","answer"));
        missions.put(Data.NULL_TYPES_DETERMINSIC,new KnownAnswerMission("ddd",null,"question","answer"));
        missions.put(Data.EMPTY_TYPE_DETERMINISTIC,new KnownAnswerMission("ddd",new HashSet<>(),"question","answer"));
        missions.put(Data.TYPES_WITH_NULL_DETERMINISTIC,new KnownAnswerMission("ddd",typesNull,"question","answer"));
        missions.put(Data.NULL_QUESTION_DETERMINISTIC,new KnownAnswerMission("ddd",types,null,"answer"));
        missions.put(Data.EMPTY_QUESTION_DETERMINISTIC,new KnownAnswerMission("ddd",types,"","answer"));
        missions.put(Data.NULL_ANSWER_DETERMINISTIC,new KnownAnswerMission("ddd",types,"question",null));
        missions.put(Data.EMPTY_ANSWER_DETERMINISTIC,new KnownAnswerMission("ddd",types,"question",""));
    }

    private void initTeacher() {
        teachers=new HashMap<Data, Teacher>();
        teachers.put(Data.VALID_WITH_PASSWORD,new Teacher("NoAlasTeacher","Avi","Ron","1234"));
        teachers.put(Data.VALID_WITH_CLASSROOM,new Teacher("NoAlasTeacher","Avi","Ron",
                classRoomMap.get(Data.Valid_Classroom),GroupType.BOTH,"1234"));
        teachers.put(Data.MOCK,new TeacherMock("NoAlasTeacher","Avi","Ron","1234"));
        teachers.put(Data.WRONG_NAME,new Teacher("Wrong","Avi","Ron","1234"));
    }

    private void initVerificationCodes() {
        verificationCodes=new HashMap<Data, String>();
        verificationCodes.put(Data.NULL_CODE,null);
        verificationCodes.put(Data.EMPTY_CODE,"");
    }

    private void initRegisterDetailsDatas() {
        registerDetailsDatas=new HashMap<Data, RegisterDetailsData>();
        registerDetailsDatas.put(Data.VALID,new RegisterDetailsData("NoAlasIsExistWithThatName","1234"));
        registerDetailsDatas.put(Data.VALID2,new RegisterDetailsData("NoAlasIsExistWithThatName","1234"));
        registerDetailsDatas.put(Data.NULL_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName",null));
        registerDetailsDatas.put(Data.EMPTY_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName",""));
        registerDetailsDatas.put(Data.NULL_ALIAS,new RegisterDetailsData(null,"1234"));
        registerDetailsDatas.put(Data.EMPTY_ALIAS,new RegisterDetailsData("","1234"));
        registerDetailsDatas.put(Data.WRONG_NAME,new RegisterDetailsData("Wrong","1234"));

    }

    private void initStudents() {
        students=new HashMap<Data, Student>();
        students.put(Data.VALID,new Student("NoAlasIsExistWithThatName","Yuval","Sabag"));
    }

    public Student getStudent(Data data){
        return students.get(data);
    }

    public RegisterDetailsData getRegisterDetails(Data data) {
        return registerDetailsDatas.get(data);
    }

    public String getVerificationCode (Data data){
        return verificationCodes.get(data);
    }

    public void setValidVerificationCode(String code) {
        verificationCodes.put(Data.VALID,code);
        verificationCodes.put(Data.WRONG_CODE,code+"3");
    }

    public Teacher getTeacher(Data data) {
        return teachers.get(data);
    }

    public Mission getMission(Data data) {
        return missions.get(data);
    }

    public RoomTemplateDetailsData getRoomTemplateData(Data data){
        return roomTemplatesDatas.get(data);
    }

    public RoomTemplate getRoomTemplate(Data data){
        return roomTemplates.get(data);
    }

    public Room getRoom(Data data) {
        return roomsMap.get(data);
    }

    public newRoomDetails getNewRoomDetails(Data data) {
        return newRoomDetailsMap.get(data);
    }

    public Classroom getClassroom(Data data){
        return classRoomMap.get(data);
    }
}
