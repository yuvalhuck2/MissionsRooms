package Data;

import DataAPI.RegisterDetailsData;
import DataAPI.RoomType;
import DataAPI.UserType;
import ExternalSystems.HashSystem;
import javafx.util.Pair;
import missions.room.Domain.IT;
import missions.room.Domain.Mission;
import missions.room.Domain.User;
import missions.room.Domain.Student;
import missions.room.Domain.Teacher;
import missions.room.Domain.missions.KnownAnswerMission;
import DomainMocks.TeacherMock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataGenerator {

    private HashMap<Data, Student> students;
    private HashMap<Data,RegisterDetailsData> registerDetailsDatas;
    private HashMap<Data,String> verificationCodes;
    private HashMap<Data, Teacher> teachers;
    private HashMap<Data, Mission> missions;
    private HashMap<Data, Pair<String,String>> loginDatas;
    private HashMap<Data, User> users;
    private final HashSystem hashSystem= new HashSystem();;

    public DataGenerator() {
        initStudents();
        initRegisterDetailsDatas();
        initVerificationCodes();
        initTeacher();
        initMissions();
        initLoginDatas();
        initUsers();
    }

    private void initUsers(){
        users=new HashMap<Data, User>();
        User validStudent=new Student("ExistAliasStudent","Gal","Haviv");
        validStudent.setPassword(hashSystem.encrypt("1234"));
        users.put(Data.VALID_STUDENT,validStudent);

        User validTeacher=new Teacher("ExistAliasTeacher","Tal","Cohen",hashSystem.encrypt("1234"));
        users.put(Data.VALID_TEACHER,validTeacher);

        User validIT=new IT("ExistAliasIT",hashSystem.encrypt("1234"));
        users.put(Data.VALID_IT,validIT);


    }

    private void initMissions() {
        missions=new HashMap<Data, Mission>();
        Set<RoomType> types=new HashSet<>();
        types.add(RoomType.Personal);
        missions.put(Data.Valid_Deterministic,new KnownAnswerMission("ddd",types,"question","answer"));
        missions.put(Data.WRONG_TYPE_DETERMINSIC,new KnownAnswerMission("ddd",new HashSet<>(),"question","answer"));
        missions.put(Data.NULL_QUESTION_DETERMINISTIC,new KnownAnswerMission("ddd",types,null,"answer"));
        missions.put(Data.EMPTY_QUESTION_DETERMINISTIC,new KnownAnswerMission("ddd",types,"","answer"));
        missions.put(Data.NULL_ANSWER_DETERMINISTIC,new KnownAnswerMission("ddd",types,"question",null));
        missions.put(Data.EMPTY_ANSWER_DETERMINISTIC,new KnownAnswerMission("ddd",types,"question",""));
    }

    private void initTeacher() {
        teachers=new HashMap<Data, Teacher>();
        teachers.put(Data.VALID_WITH_PASSWORD,new Teacher("NoAlasTeacher","Avi","Ron","1234"));
        teachers.put(Data.MOCK,new TeacherMock("NoAlasTeacher","Avi","Ron","1234"));
    }

    private void initVerificationCodes() {
        verificationCodes=new HashMap<Data, String>();
        verificationCodes.put(Data.NULL_CODE,null);
        verificationCodes.put(Data.EMPTY_CODE,"");
    }

    private void initRegisterDetailsDatas() {
        registerDetailsDatas=new HashMap<Data, RegisterDetailsData>();
        registerDetailsDatas.put(Data.VALID,new RegisterDetailsData("NoAlasIsExistWithThatName","1234", UserType.Student));
        registerDetailsDatas.put(Data.NULL_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName",null, UserType.Student));
        registerDetailsDatas.put(Data.EMPTY_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName","", UserType.Student));
        registerDetailsDatas.put(Data.NULL_ALIAS,new RegisterDetailsData(null,"1234", UserType.Student));
        registerDetailsDatas.put(Data.EMPTY_ALIAS,new RegisterDetailsData("","1234", UserType.Student));
        registerDetailsDatas.put(Data.WRONG_TYPE,new RegisterDetailsData("NoAlasIsExistWithThatName","1234", UserType.IT));
        registerDetailsDatas.put(Data.WRONG_NAME,new RegisterDetailsData("Wrong","1234", UserType.Student));

    }

    private void initStudents() {
        students=new HashMap<Data, Student>();
        students.put(Data.VALID,new Student("NoAlasIsExistWithThatName","Yuval","Sabag"));
    }



    private void initLoginDatas(){
        loginDatas=new HashMap<Data, Pair<String, String>>();
        loginDatas.put(Data.VALID_STUDENT,new Pair<>("ExistAliasStudent","1234"));
        loginDatas.put(Data.VALID_TEACHER,new Pair<>("ExistAliasTeacher","1234"));
        loginDatas.put(Data.VALID_IT,new Pair<>("ExistAliasIT","1234"));
        loginDatas.put(Data.NULL_ALIAS,new Pair<>(null,"1234"));
        loginDatas.put(Data.NULL_PASSWORD,new Pair<>("ExistAlias",null));
        loginDatas.put(Data.EMPTY_ALIAS,new Pair<>("","1234"));
        loginDatas.put(Data.EMPTY_PASSWORD,new Pair<>("ExistAlias",""));
        loginDatas.put(Data.NOT_EXIST_ALIAS,new Pair<>("NotExistAlias","1234"));
        loginDatas.put(Data.WRONG_PASSWORD,new Pair<>("ExistAliasStudent","1111"));

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

    public Pair<String,String> getLoginDetails(Data data){
        return loginDatas.get(data);
    }

    public User getUser(Data data){
        return users.get(data);
    }
}
