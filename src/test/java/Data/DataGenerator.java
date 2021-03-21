package Data;

import DataAPI.*;
import missions.room.Domain.*;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.StudentRoom;
import DataAPI.RegisterDetailsData;
import DataAPI.RoomTemplateDetailsData;
import DataAPI.RoomType;
import ExternalSystems.HashSystem;
import javafx.util.Pair;
import missions.room.Domain.Users.*;
import missions.room.Domain.missions.KnownAnswerMission;
import DomainMocks.TeacherMock;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.missions.OpenAnswerMission;
import missions.room.Domain.missions.StoryMission;

import java.util.*;

public class DataGenerator {

    private HashMap<Data, Student> students;
    private HashMap<Data, StudentData> studentDatas;
    private HashMap<Data,RegisterDetailsData> registerDetailsDatas;
    private HashMap<Data,String> verificationCodes;
    private HashMap<Data, Teacher> teachers;
    private HashMap<Data, Mission> missions;
    private HashMap<Data, Pair<String,String>> loginDatas;
    private HashMap<Data, User> users;
    private final HashSystem hashSystem= new HashSystem();;
    private HashMap<Data, RoomTemplate> roomTemplates;
    private HashMap<Data, RoomTemplateDetailsData> roomTemplatesDatas;
    private HashMap<Data, Room> roomsMap;
    private HashMap<Data, NewRoomDetails> newRoomDetailsMap;
    private HashMap<Data,Classroom> classRoomMap;
    private HashMap<Data,ClassRoomData> classRoomDataMap;
    private HashMap<Data,ClassGroup> classGroupMap;
    private HashMap<Data,GroupData> classGroupData;
    private HashMap<Data,Suggestion> suggestionHashMap;
    private HashMap<Data, String> triviaSubjectHashMap;
    private HashMap<Data, TriviaQuestionData> triviaQuestionHashMap;
    private HashMap<Data, MessageData> messageDataMap;
    private HashMap<Data,HashSet<PointsData>> pointsDataSets;
    private HashMap<Data,UserProfileData> userProfileDataMap;

    public DataGenerator() {
        initStudents();
        initStudentDataDatas();
        initSuggestions();
        initGroups();
        initGroupsDatas();
        initClassrooms();
        initClassroomData();
        initRegisterDetailsDatas();
        initVerificationCodes();
        initTeacher();
        initMissions();
        initRoomTemplateDatas();
        initRoomTemplates();
        initRooms();
        initNewRoomDetails();
        initLoginDatas();
        initUsers();
        initTriviaData();
        initMessagesData();
        initPointsDataSet();
        initUserProfileDataMap();
    }

    private void initUserProfileDataMap() {
        userProfileDataMap = new HashMap<Data, UserProfileData>();
        String alias = students.get(Data.VALID).getAlias();
        String fName= "first";
        String lName = "last";
        userProfileDataMap.put(Data.VALID,new UserProfileData(alias,lName,fName));
        userProfileDataMap.put(Data.NULL_LAST_NAME,new UserProfileData(alias,null,fName));
        userProfileDataMap.put(Data.NULL_NAME,new UserProfileData(alias,lName,null));
        userProfileDataMap.put(Data.NULL,new UserProfileData(alias,null,null));
        userProfileDataMap.put(Data.NULL_ALIAS,new UserProfileData(DataConstants.WRONG_USER_NAME,lName,fName));
    }

    private void initPointsDataSet() {
        pointsDataSets = new HashMap<Data, HashSet<PointsData>>();
        HashSet<PointsData> hashSet = new HashSet<>();
        HashSet<PointsData> groupHashSet = new HashSet<>();
        Student student =students.get(Data.VALID);
        hashSet.add(new PointsData(student.getAlias(),student.getPoints()));
        student =students.get(Data.VALID2);
        hashSet.add(new PointsData(student.getAlias(),student.getPoints()));

        pointsDataSets.put(Data.Valid_Student,hashSet);
        hashSet = new HashSet<>();
        Classroom classroom = classRoomMap.get(Data.Valid_Classroom);
        hashSet.add(new PointsData(classroom.getClassHebrewName(),classroom.getPoints()));
        groupHashSet.add(new PointsData(classroom.getClassHebrewName()+" A",classroom.getPoints()));
        groupHashSet.add(new PointsData(classroom.getClassHebrewName()+" B",classroom.getPoints()));
        classroom = classRoomMap.get(Data.Valid_2Students_From_Different_Groups);
        hashSet.add(new PointsData(classroom.getClassHebrewName(),classroom.getPoints()));
        groupHashSet.add(new PointsData(classroom.getClassHebrewName()+" A",classroom.getPoints()));

        pointsDataSets.put(Data.Valid_Classroom,hashSet);
        pointsDataSets.put(Data.Valid_Group,groupHashSet);

        hashSet = new HashSet<>();
        student =students.get(Data.VALID);
        hashSet.add(new PointsData(student.getAlias(),student.getPoints(), true));
        student =students.get(Data.VALID2);
        hashSet.add(new PointsData(student.getAlias(),student.getPoints()));

        pointsDataSets.put(Data.VALID_TEACHER,hashSet);
    }

    private void initMessagesData() {
        messageDataMap=new HashMap<Data, MessageData>();
        messageDataMap.put(Data.VALID,new MessageData("messageId",
                "hello",
                students.get(Data.VALID).getAlias(), "01/02/2030", "14:34"));
    }

    private void initTriviaData() {
        triviaSubjectHashMap = new HashMap<Data, String>();
        triviaSubjectHashMap.put(Data.VALID,"valid subject");
        triviaSubjectHashMap.put(Data.VALID2,"valid subject2");
        triviaSubjectHashMap.put(Data.VALID3,"valid subject3");
        triviaSubjectHashMap.put(Data.INVALID, "");
        triviaQuestionHashMap = new HashMap<Data, TriviaQuestionData>();
        List<String> validAnswers = Arrays.asList("ans1", "ans2");
        List<String> invalidAnswers = new ArrayList<>();
        triviaQuestionHashMap.put(Data.VALID, new TriviaQuestionData( "question?", validAnswers,
                "correctANS", triviaSubjectHashMap.get(Data.VALID)));
        triviaQuestionHashMap.put(Data.VALID2, new TriviaQuestionData( "question?", validAnswers,
                "correctANS", triviaSubjectHashMap.get(Data.VALID2)));
        triviaQuestionHashMap.put(Data.TRIVIA_INVALID_QUESTION, new TriviaQuestionData( "", validAnswers,
                "correctANS", triviaSubjectHashMap.get(Data.VALID)));
        triviaQuestionHashMap.put(Data.TRIVIA_INVALID_ANSWERS, new TriviaQuestionData( "question?", invalidAnswers,
                "correctANS", triviaSubjectHashMap.get(Data.VALID)));
        triviaQuestionHashMap.put(Data.TRIVIA_INVALID_CORRECT_ANSWER, new TriviaQuestionData( "question?", validAnswers,
                "", triviaSubjectHashMap.get(Data.VALID)));

    }
    private void initSuggestions() {
        suggestionHashMap=new HashMap<Data, Suggestion>();
        suggestionHashMap.put(Data.VALID,new Suggestion("id","suggest something"));
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

        User validIT2=new IT("ExistAliasIT2",hashSystem.encrypt("1234"));
        users.put(Data.VALID_IT2,validIT2);


        initRoomTemplateDatas();
        initRoomTemplates();
        initRooms();
        initNewRoomDetails();
    }

    private void initClassrooms() {
        classRoomMap=new HashMap<Data, Classroom>();
        Classroom valid=new Classroom("2=4",classGroupMap.get(Data.Empty_Students),classGroupMap.get(Data.Valid_Group));
        classRoomMap.put(Data.Valid_Classroom,valid);
        List<ClassGroup> groups=new ArrayList<>();
        groups.add(classGroupMap.get(Data.Empty_Students));
        groups.add(classGroupMap.get(Data.VALID_WITH_GROUP_C));
        classRoomMap.put(Data.VALID_WITH_GROUP_C,new Classroom("1=3",groups));
        Classroom valid2StudentsFromDifferentGroups=new Classroom("0=2"
                ,classGroupMap.get(Data.Valid_Group2)
                ,classGroupMap.get(Data.Valid_Group));
        classRoomMap.put(Data.Valid_2Students_From_Different_Groups,
                valid2StudentsFromDifferentGroups);
    }

    private void initClassroomData() {
        classRoomDataMap=new HashMap<Data, ClassRoomData>();
        initClassRoomDatasFromGroupsMap(Data.Valid_Classroom);
    }

    private void initClassRoomDatasFromGroupsMap(Data data){
        classRoomDataMap.put(data,classRoomMap.get(data).getClassroomData(GroupType.BOTH));
    }

    private void initGroups() {
        classGroupMap=new HashMap<Data, ClassGroup>();
        HashMap<String,Student> studentHashMap=new HashMap<>();
        studentHashMap.put(students.get(Data.VALID).getAlias(),students.get(Data.VALID));
        classGroupMap.put(Data.Valid_Group,new ClassGroup("g2",GroupType.B,studentHashMap));
        classGroupMap.put(Data.Empty_Students,new ClassGroup("g1",GroupType.A,new HashMap<>()));
        classGroupMap.put(Data.VALID_WITH_GROUP_C,new ClassGroup("g3",GroupType.C,studentHashMap));
        HashMap<String,Student> student2HashMap=new HashMap<>();
        student2HashMap.put(students.get(Data.VALID2).getAlias(),students.get(Data.VALID2));
        classGroupMap.put(Data.Valid_Group2,new ClassGroup("valid2",GroupType.A,student2HashMap));
    }

    private void initGroupsDatas(){
        classGroupData=new HashMap<Data, GroupData>();
        initGroupDatasFromGroupsMap(Data.Valid_Group);
        initGroupDatasFromGroupsMap(Data.Empty_Students);

    }

    private void initGroupDatasFromGroupsMap(Data data){
        classGroupData.put(data,classGroupMap.get(data).getGroupData(GroupType.BOTH));
    }

    private void initNewRoomDetails() {
        newRoomDetailsMap =new HashMap<Data, NewRoomDetails>();
        String student=students.get(Data.VALID).getAlias();
        String group=classGroupMap.get(Data.Valid_Group).getGroupName();
        String classroom =classRoomMap.get(Data.Valid_Classroom).getClassName();
        String teacher=teachers.get(Data.VALID_WITH_CLASSROOM).getAlias();
        String roomTemplateStudent=roomTemplates.get(Data.VALID).getRoomTemplateId();
        String roomTemplateGroup=roomTemplates.get(Data.Valid_Group).getRoomTemplateId();
        String roomTemplateClassroom=roomTemplates.get(Data.Valid_Classroom).getRoomTemplateId();
        newRoomDetailsMap.put(Data.Valid_Student,new NewRoomDetails(student,RoomType.Personal,"name",teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.Valid_Group,new NewRoomDetails(group,RoomType.Group,"name",teacher,roomTemplateGroup,3));
        newRoomDetailsMap.put(Data.Valid_Classroom,new NewRoomDetails(classroom,RoomType.Class,"name",teacher,roomTemplateClassroom,3));
        newRoomDetailsMap.put(Data.NULL_NAME,new NewRoomDetails(student,RoomType.Personal,null,teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.EMPTY_NAME,new NewRoomDetails(student,RoomType.Personal,"",teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.NULL,null);
        newRoomDetailsMap.put(Data.NEG_AMOUNT,new NewRoomDetails(student,RoomType.Personal,"name",teacher,roomTemplateStudent,-1));
        newRoomDetailsMap.put(Data.TYPE_NOT_MATCH,new NewRoomDetails(group,RoomType.Group,"name",teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.NOT_EXIST_CLASSROOM,new NewRoomDetails("Wrong",RoomType.Class,"name",teacher,roomTemplateClassroom,3));
        newRoomDetailsMap.put(Data.NOT_EXIST_CLASSGROUP,new NewRoomDetails("Wrong",RoomType.Group,"name",teacher,roomTemplateGroup,3));
        newRoomDetailsMap.put(Data.NOT_EXIST_STUDENT,new NewRoomDetails("Wrong",RoomType.Personal,"name",teacher,roomTemplateStudent,3));
        newRoomDetailsMap.put(Data.NOT_EXIST_TEMPLATE,new NewRoomDetails(student,RoomType.Personal,"name",teacher,roomTemplateStudent+"1",3));
    }

    private void initRooms() {
        roomsMap=new HashMap<Data, Room>();
        String alias=students.get(Data.VALID).getAlias();
        Room studentRoom=new StudentRoom("roomId","name",students.get(Data.VALID),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.VALID),3);
        roomsMap.put(Data.Valid_Student,studentRoom);
        roomsMap.put(Data.Valid_Group,new GroupRoom("roomId1","name",classGroupMap.get(Data.Valid_Group),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.Valid_Group),3));
        roomsMap.put(Data.Valid_Classroom,new ClassroomRoom("roomId2","name",classRoomMap.get(Data.Valid_Classroom),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.Valid_Classroom),3));
        roomsMap.put(Data.NULL_NAME,new StudentRoom("roomId",null,students.get(Data.VALID),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.VALID),3));
        roomsMap.put(Data.EMPTY_NAME,new StudentRoom("roomId","",students.get(Data.VALID),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.VALID),3));
        roomsMap.put(Data.VALID_2MissionStudent,new StudentRoom("roomId5","name",students.get(Data.VALID),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.VALID_2MissionStudent),3));
        roomsMap.put(Data.VALID_2Mission_Group,new GroupRoom("roomIdGroup","name",classGroupMap.get(Data.Valid_Group),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.VALID_2Mission_Group),3));
        roomsMap.put(Data.VALID_2Mission_Class,new ClassroomRoom("roomIdClass","name",classRoomMap.get(Data.Valid_Classroom),teachers.get(Data.VALID_WITH_CLASSROOM),roomTemplates.get(Data.VALID_2Mission_Class),3));

        Room openAnsRoom = new StudentRoom("rid1", "name", students.get(Data.VALID), teachers.get(Data.VALID_WITH_CLASSROOM), roomTemplates.get(Data.VALID_OPEN_ANS),3);
        openAnsRoom.addOpenAnswer(new OpenAnswer("open1", "answer", null));
        roomsMap.put(Data.VALID_OPEN_ANS, openAnsRoom);

        roomsMap.get(Data.VALID_OPEN_ANS).connect(alias);
        roomsMap.get(Data.Valid_Group).connect(alias);
        roomsMap.get(Data.Valid_Classroom).connect(alias);
        roomsMap.get(Data.VALID_2MissionStudent).connect(alias);
        roomsMap.get(Data.VALID_2Mission_Group).connect(alias);
        roomsMap.get(Data.VALID_2Mission_Class).connect(alias);
        studentRoom.connect(alias);

        roomsMap.put(Data.Valid_2Students_From_Different_Groups,
                new ClassroomRoom("roomIdClassDifferentGroupsStudents",
                        "name2",
                        classRoomMap.get(Data.Valid_2Students_From_Different_Groups),
                        teachers.get(Data.Valid_2Students_From_Different_Groups),
                        roomTemplates.get(Data.VALID_2Mission_Class),
                        3));

        roomsMap.put(Data.VALID_STORY,
                new ClassroomRoom("roomIdStory",
                        "storyRoom",
                        classRoomMap.get(Data.Valid_2Students_From_Different_Groups),
                        teachers.get(Data.Valid_2Students_From_Different_Groups),
                        roomTemplates.get(Data.VALID_STORY),
                        3));
    }

    private void initRoomTemplateDatas() {
        roomTemplatesDatas=new HashMap<Data, RoomTemplateDetailsData>();
        String missionId=getMission(Data.Valid_Deterministic).getMissionId();
        List<String> missions=new ArrayList<>();
        missions.add(missionId);
        List<String> missions2=new ArrayList<>();
        missions2.add(missionId);
        missions2.add(getMission(Data.Valid_Deterministic_All_Types).getMissionId());
        List<String> missions2NonPersonal=new ArrayList<>();
        missions2NonPersonal.add(getMission(Data.Valid_Deterministic_All_Types).getMissionId());
        missions2NonPersonal.add(getMission(Data.Valid_Deterministic_All_Types_2).getMissionId());
        List<String> missionsOpenAns =  new ArrayList<String>(){{add(getMission(Data.VALID_OPEN_ANS).getMissionId());}};
        roomTemplatesDatas.put(Data.VALID,new RoomTemplateDetailsData(missions,"name",1,RoomType.Personal));
        roomTemplatesDatas.put(Data.VALID_2MissionStudent,new RoomTemplateDetailsData(missions2,"name",1,RoomType.Personal));
        roomTemplatesDatas.put(Data.VALID_2Mission_Group,new RoomTemplateDetailsData(missions2NonPersonal,"name",1,RoomType.Group));
        roomTemplatesDatas.put(Data.VALID_2Mission_Class,new RoomTemplateDetailsData(missions2NonPersonal,"name",1,RoomType.Class));
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
        roomTemplatesDatas.put(Data.VALID_OPEN_ANS, new RoomTemplateDetailsData("tid1",missionsOpenAns, "name", 1, RoomType.Personal));
        List<String> missionsWithNull=new ArrayList<>();
        missionsWithNull.add("not exist");
        roomTemplatesDatas.put(Data.WRONG_ID,new RoomTemplateDetailsData(missionsWithNull,"name",1,RoomType.Personal));

        List<String> storyMission=new ArrayList<>();
        storyMission.add(getMission(Data.VALID_STORY).getMissionId());
        storyMission.add(getMission(Data.VALID_STORY2).getMissionId());
        roomTemplatesDatas.put(Data.VALID_STORY,new RoomTemplateDetailsData(storyMission,"story",1,RoomType.Class));
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

        List<Mission> missionMapOpenAns = new ArrayList<Mission>(){{add(getMission(Data.VALID_OPEN_ANS));}};

        RoomTemplateDetailsData templateDetailsDataGroup=getRoomTemplateData(Data.Valid_Group);
        templateDetailsDataGroup.setId("group");

        RoomTemplateDetailsData templateDetailsDataClass=getRoomTemplateData(Data.Valid_Classroom);
        templateDetailsDataClass.setId("class");

        roomTemplates.put(Data.VALID,new RoomTemplate(templateDetailsData,missionsMap));
        roomTemplates.put(Data.Valid_Group,new RoomTemplate(templateDetailsDataGroup,missionsMapAllTypes));
        roomTemplates.put(Data.Valid_Classroom,new RoomTemplate(templateDetailsDataClass,missionsMapAllTypes));
        roomTemplates.put(Data.VALID_OPEN_ANS, new RoomTemplate(getRoomTemplateData(Data.VALID_OPEN_ANS), missionMapOpenAns));

        List<Mission> missionsMap2Missions=new ArrayList<>();
        missionsMap2Missions.add(getMission(Data.Valid_Deterministic));
        missionsMap2Missions.add(getMission(Data.Valid_Deterministic_All_Types));

        RoomTemplateDetailsData roomTemplateDetailsData=getRoomTemplateData(Data.VALID_2MissionStudent);
        roomTemplateDetailsData.setId("twoMissionsStudent");
        roomTemplates.put(Data.VALID_2MissionStudent,new RoomTemplate(roomTemplateDetailsData,missionsMap2Missions));


        List<Mission> missionsMap2MissionsNonPersonal=new ArrayList<>();
        missionsMap2MissionsNonPersonal.add(getMission(Data.Valid_Deterministic_All_Types_2));
        missionsMap2MissionsNonPersonal.add(getMission(Data.Valid_Deterministic_All_Types));
        roomTemplateDetailsData=getRoomTemplateData(Data.VALID_2Mission_Group);
        roomTemplateDetailsData.setId("twoMissionsGroup");
        roomTemplates.put(Data.VALID_2Mission_Group,new RoomTemplate(roomTemplateDetailsData,missionsMap2MissionsNonPersonal));

        roomTemplateDetailsData=getRoomTemplateData(Data.VALID_2Mission_Class);
        roomTemplateDetailsData.setId("twoMissionsClass");
        roomTemplates.put(Data.VALID_2Mission_Class,new RoomTemplate(roomTemplateDetailsData,missionsMap2MissionsNonPersonal));

        List<Mission> storyMission=new ArrayList<>();
        storyMission.add(getMission(Data.VALID_STORY));
        storyMission.add(getMission(Data.VALID_STORY2));
        roomTemplateDetailsData=getRoomTemplateData(Data.VALID_STORY);
        roomTemplateDetailsData.setId("story template");
        roomTemplates.put(Data.VALID_STORY,new RoomTemplate(roomTemplateDetailsData,storyMission));
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
        missions.put(Data.Valid_Deterministic_All_Types,new KnownAnswerMission("ddd1",allTypes,"question","answer"));
        missions.put(Data.Valid_Deterministic_All_Types_2,new KnownAnswerMission("ddd2",allTypes,"question","answer"));
        missions.put(Data.NULL_TYPES_DETERMINSIC,new KnownAnswerMission("ddd",null,"question","answer"));
        missions.put(Data.EMPTY_TYPE_DETERMINISTIC,new KnownAnswerMission("ddd",new HashSet<>(),"question","answer"));
        missions.put(Data.TYPES_WITH_NULL_DETERMINISTIC,new KnownAnswerMission("ddd",typesNull,"question","answer"));
        missions.put(Data.NULL_QUESTION_DETERMINISTIC,new KnownAnswerMission("ddd",types,null,"answer"));
        missions.put(Data.EMPTY_QUESTION_DETERMINISTIC,new KnownAnswerMission("ddd",types,"","answer"));
        missions.put(Data.NULL_ANSWER_DETERMINISTIC,new KnownAnswerMission("ddd",types,"question",null));
        missions.put(Data.EMPTY_ANSWER_DETERMINISTIC,new KnownAnswerMission("ddd",types,"question",""));
        missions.put(Data.VALID_STORY,new StoryMission("story",types,""));
        missions.put(Data.VALID_STORY2,new StoryMission("story2",types,""));
        missions.put(Data.VALID_OPEN_ANS, new OpenAnswerMission("open1", types, "question"));
    }

    private void initTeacher() {
        teachers=new HashMap<Data, Teacher>();
        teachers.put(Data.VALID_WITH_PASSWORD,new Teacher("NoAlasTeacher","Avi","Ron","1234"));
        teachers.put(Data.Supervisor,new Supervisor("supervisorAlias","Avi","Ron",null,GroupType.BOTH));
        teachers.put(Data.VALID_WITH_CLASSROOM,new Teacher("NoAlasTeacher","Avi","Ron",
                classRoomMap.get(Data.Valid_Classroom),GroupType.BOTH,"1234"));
        teachers.put(Data.VALID_WITH_GROUP_C,new Teacher("NoAlasTeacher","Avi","Ron",
                classRoomMap.get(Data.VALID_WITH_GROUP_C),GroupType.BOTH,"1234"));
        teachers.put(Data.MOCK,new TeacherMock("NoAlasTeacher","Avi","Ron","1234"));
        teachers.put(Data.WRONG_NAME,new Teacher("Wrong","Avi","Ron","1234"));
        teachers.put(Data.Valid_2Students_From_Different_Groups,new Teacher("2StudentsTeacher","name","L name",
                classRoomMap.get(Data.Valid_2Students_From_Different_Groups),GroupType.BOTH,"1234"));
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
        registerDetailsDatas.put(Data.VALID_IT,new RegisterDetailsData("ExistAliasIT","1234"));
        registerDetailsDatas.put(Data.VALID_IT2,new RegisterDetailsData("ExistAliasIT2","1234"));
        registerDetailsDatas.put(Data.NULL_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName",null));
        registerDetailsDatas.put(Data.EMPTY_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName",""));
        registerDetailsDatas.put(Data.NULL_ALIAS,new RegisterDetailsData(null,"1234"));
        registerDetailsDatas.put(Data.EMPTY_ALIAS,new RegisterDetailsData("","1234"));
        registerDetailsDatas.put(Data.WRONG_NAME,new RegisterDetailsData("Wrong","1234"));

    }

    private void initStudents() {
        students=new HashMap<Data, Student>();
        students.put(Data.VALID,new Student("NoAlasIsExistWithThatName","Yuval","Sabag",4));
        students.put(Data.VALID2,new Student("valid2Alias","Tal","Cohen"));
    }

    private void initStudentDataDatas(){
        studentDatas=new HashMap<Data, StudentData>();
        initSingleStudentData(Data.VALID);
    }

    private void initSingleStudentData(Data data){
        studentDatas.put(data,students.get(data).getStudentData());
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

    public RoomTemplateDetailsData getRoomTemplateData(Data data){
        return roomTemplatesDatas.get(data);
    }

    public RoomTemplate getRoomTemplate(Data data){
        return roomTemplates.get(data);
    }

    public Room getRoom(Data data) {
        return roomsMap.get(data);
    }

    public NewRoomDetails getNewRoomDetails(Data data) {
        return newRoomDetailsMap.get(data);
    }

    public Classroom getClassroom(Data data){
        return classRoomMap.get(data);
    }

    public StudentData getStudentData(Data data) {
        return studentDatas.get(data);
    }

    public GroupData getGroupData(Data data) {
        return classGroupData.get(data);
    }

    public ClassGroup getClassGroup(Data data) {
        return classGroupMap.get(data);
    }

    public ClassRoomData getClassroomData(Data data) {
        return classRoomDataMap.get(data);
    }

    public Suggestion getSuggestion(Data data) {
        return suggestionHashMap.get(data);
    }

    public  String getTriviaSubject(Data data) {
        return triviaSubjectHashMap.get(data);
    }

    public TriviaQuestionData getTriviaQuestion(Data data) {
        return triviaQuestionHashMap.get(data);
    }

    public MessageData getMessageData(Data data){ return messageDataMap.get(data); }

    public HashSet<PointsData> getPointsHasSet(Data data){ return pointsDataSets.get(data); }

    public UserProfileData getProfileData(Data data) {
        return userProfileDataMap.get(data);
    }
}
