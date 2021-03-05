package missions.room.Domain.Rooms;

import DataAPI.*;
import Utils.Utils;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.RoomMessage;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.TriviaQuestion;
import missions.room.Domain.Users.Teacher;
import missions.room.Domain.missions.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Room {

    private static final String STORY_MISSION_NAME = "Story_Mission";

    @Id
    protected String roomId;

    protected String name;

    protected int bonus;

    protected int currentMission;

    protected int countCorrectAnswer;

    @Transient
    protected Set<String>  studentWereChosen;

    @Transient
    protected Set<String>  studentWereChosenForStory;

    @Transient
    protected Set<String>  connectedStudents;

    @Transient
    protected String  missionIncharge;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="roomId",referencedColumnName = "roomId")
    protected List<RoomMessage> roomMessages;

    @OneToOne
    protected Teacher teacher;

    @OneToOne
    protected RoomTemplate roomTemplate;

    public Room() {
        studentWereChosen=new HashSet<>();
        connectedStudents=new HashSet<>();
        studentWereChosenForStory=new HashSet<>();

    }

    public Room(String roomId,String name,Teacher teacher,RoomTemplate roomTemplate,int bonus) {
        this.roomId = roomId;
        this.name=name;
        this.teacher=teacher;
        this.roomTemplate=roomTemplate;
        studentWereChosen=new HashSet<>();
        this.bonus=bonus;
        this.currentMission=0;
        this.countCorrectAnswer=0;
        studentWereChosen=new HashSet<>();
        connectedStudents=new HashSet<>();
        studentWereChosenForStory=new HashSet<>();
    }

    public String drawMissionInCharge() {
        studentWereChosen.add(missionIncharge);
        Set<String> studentsToChooseFrom=new HashSet<>(connectedStudents);
        studentsToChooseFrom.removeAll(studentWereChosen);
        if(studentsToChooseFrom.isEmpty()){
            studentWereChosen.clear();
            studentsToChooseFrom=connectedStudents;
        }
        missionIncharge=Utils.getRandomFromSet(studentsToChooseFrom);
        return missionIncharge;
    }

    public String drawMissionInChargeForStory() {
        studentWereChosenForStory.add(missionIncharge);
        Set<String> studentsToChooseFrom=new HashSet<>(connectedStudents);
        studentsToChooseFrom.removeAll(studentWereChosenForStory);
        if(studentsToChooseFrom.isEmpty()){
            missionIncharge=null;
        }
        else {
            missionIncharge = Utils.getRandomFromSet(studentsToChooseFrom);
        }
        return missionIncharge;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public int getBonus() {
        return bonus;
    }

    public int getCurrentMissionIndex() {
        return currentMission;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public RoomTemplate getRoomTemplate() {
        return roomTemplate;
    }

    private void addCorrectAnswer(){
        this.countCorrectAnswer++;
    }

    public void increaseCurrentMission(){
        this.currentMission++;
    }

    public int getCountCorrectAnswer() {
        return countCorrectAnswer;
    }

    public void setCountCorrectAnswer(int countCorrectAnswer) {
        this.countCorrectAnswer = countCorrectAnswer;
    }

    public RoomType updatePoints(){
        addCorrectAnswer();
        int points=roomTemplate.getMission(currentMission).getPoints();
        return addPoints(points);
    }

    protected abstract RoomType addPoints(int points);

    public boolean isLastMission(){
        return currentMission>=roomTemplate.getMissions().size()-1;
    }

    protected boolean toCloseRoom(){
        return isLastMission()&&allOpenQuestionsApproved();
    }

    //TODO implement after doing openRoomMission
    private boolean allOpenQuestionsApproved() {
        return true;
    }

    public RoomDetailsData getData() {
        Mission mission = roomTemplate.getMission(currentMission);
        if(mission==null){
            return null;
        }
        MissionData missionData = mission.getData();
        return new RoomDetailsData(roomId,name,missionData,roomTemplate.getType());
    }


    //TODO refactor to the mission object to return it's own data
    private MissionData getMissionData(Mission mission) {
        MissionData md = new MissionData(mission.getMissionId(), mission.getMissionTypes());
        List<String> questList = new ArrayList<>();
        List<String> answerList = new ArrayList<>();
        if (mission instanceof KnownAnswerMission) {
            md.setName("Known answer mission");
            questList.add(((KnownAnswerMission) mission).getQuestion());
            md.setQuestion(questList);
            answerList.add(((KnownAnswerMission) mission).getRealAnswer());
            md.setAnswers(answerList);
        }
        if (mission instanceof OpenAnswerMission) {
            md.setName("Open Answer Mission");
            questList.add(((OpenAnswerMission) mission).getQuestion());
        }
        if (mission instanceof StoryMission) {
            md.setName("Story Mission");
        }
        if (mission instanceof TriviaMission) {
            md.setName("Trivia Mission");
            md.setTimeForAns(((TriviaMission) mission).getSecondsForAnswer());
            for (Map.Entry<String, TriviaQuestion> entry : ((TriviaMission) mission).getQuestions().entrySet()) {
                questList.add(entry.getValue().getQuestion());
            }
            md.setQuestion(questList);
        }
        if (mission instanceof TrueLieMission) {
            md.setName("True False Mission");
            md.setTimeForAns(((TrueLieMission) mission).getAnswerTimeForStudent());
        }
        return md;
    }

    public Set<String> getConnectedUsersAliases(){
        return connectedStudents;
    }

    public OpCode connect(String alias) {
        if(isBelongToRoom(alias)) {
            connectedStudents.add(alias);
            if (missionIncharge == null) {
                missionIncharge = alias;
                return OpCode.IN_CHARGE;
            }
            return OpCode.NOT_IN_CHARGE;
        }
        return OpCode.NOT_BELONGS_TO_ROOM;
    }

    public abstract boolean isBelongToRoom(String alias);

    public Response<String> disconnect(String alias) {
        connectedStudents.remove(alias);
        if(connectedStudents.isEmpty()){
            return new Response<>(null, OpCode.Delete);
        }
        else if(missionIncharge.equals(alias)){
            if(roomTemplate.getMission(currentMission).getMissionName().equals(STORY_MISSION_NAME)){
                return new Response<>(drawMissionInChargeForStory(),OpCode.Success);
            }
            return new Response<>(drawMissionInCharge(),OpCode.Success);
        }
        return new Response<>(null,OpCode.Success);
    }

    public String getMissionInCharge() {
        return missionIncharge;
    }

    public Mission getCurrentMission() {
        return roomTemplate.getMission(currentMission);
    }

    public boolean isEnoughConnected() {
        return (getParticipantsSize() / 2) <= connectedStudents.size();
    }

    protected abstract int getParticipantsSize();

    public boolean clearStoryMission(){
        if(studentWereChosenForStory.isEmpty())
            return false;
        studentWereChosenForStory.clear();
        return true;
    }
}
