package Data;

import DataAPI.RegisterDetailsData;
import DataAPI.RoomTemplateDetailsData;
import DataAPI.RoomType;
import DataAPI.UserType;
import missions.room.Domain.Mission;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Student;
import missions.room.Domain.Teacher;
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

    public DataGenerator() {
        initStudents();
        initRegisterDetailsDatas();
        initVerificationCodes();
        initTeacher();
        initMissions();
        initRoomTemplateDatas();
        initRoomTemplates();
    }

    private void initRoomTemplateDatas() {
        roomTemplatesDatas=new HashMap<Data, RoomTemplateDetailsData>();
        String missionId=getMission(Data.Valid_Deterministic).getMissionId();
        List<String> missions=new ArrayList<>();
        missions.add(missionId);
        roomTemplatesDatas.put(Data.VALID,new RoomTemplateDetailsData(missions,"name",1,RoomType.Personal));
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
        HashMap<String,Mission> missionsMap=new HashMap<>();
        Mission detMission=getMission(Data.Valid_Deterministic);
        missionsMap.put(detMission.getMissionId(),detMission);
        roomTemplates.put(Data.VALID,new RoomTemplate(templateDetailsData,missionsMap));
    }

    private void initMissions() {
        missions=new HashMap<Data, Mission>();
        Set<RoomType> types=new HashSet<>();
        Set<RoomType> typesNull=new HashSet<>();
        typesNull.add(null);
        types.add(RoomType.Personal);
        missions.put(Data.Valid_Deterministic,new KnownAnswerMission("ddd",types,"question","answer"));
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
        return roomTemplates.get(Data.VALID);
    }
}
