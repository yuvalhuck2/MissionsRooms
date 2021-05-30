package missions.room.Domain.Rooms;

import DataObjects.FlatDataObjects.*;
import Utils.Utils;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.RoomMessage;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Users.Teacher;
import missions.room.Domain.missions.Mission;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    protected boolean isTeacherConnect;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    protected List<OpenAnswer> openAnswers;

    @OneToOne
    protected Teacher teacher;

    @OneToOne
    protected RoomTemplate roomTemplate;

    @Transient
    private boolean waitingForStory;

    public Room() {
        studentWereChosen=new HashSet<>();
        connectedStudents=new HashSet<>();
        studentWereChosenForStory=new HashSet<>();
        waitingForStory=false;
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
        waitingForStory=false;
        this.isTeacherConnect=false;
        openAnswers = new ArrayList<>();
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
            waitingForStory=true;
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

    public boolean allQuestionsAnswered() {
        return currentMission > roomTemplate.getMissions().size() - 1;
    }

    public boolean toCloseRoom(){
        return isLastMission()&&allOpenQuestionsApproved();
    }

    public boolean allOpenQuestionsApproved() {
        return openAnswers.isEmpty();
    }

    public RoomDetailsData getData() {
        Mission mission = roomTemplate.getMission(currentMission);
        if(mission==null){
            return null;
        }
        MissionData missionData = mission.getData();
        return new RoomDetailsData(roomId,name,missionData,roomTemplate.getType(),waitingForStory);
    }

    public  RoomDetailsData getRoomDetailsData () {
        Mission mission = roomTemplate.getMission(currentMission);
        MissionData missionData = mission == null ? null : mission.getData();
        return new RoomDetailsData(roomId,name,missionData,roomTemplate.getType(),waitingForStory);
    }

    public Set<String> getConnectedUsersAliases(){
        return connectedStudents;
    }

    public OpCode connect(String alias) {
        if(isBelongToRoom(alias)) {
            connectedStudents.add(alias);
            if (missionIncharge == null&&!waitingForStory) {
                missionIncharge = alias;
                return OpCode.IN_CHARGE;
            }
            return OpCode.NOT_IN_CHARGE;
        }
        else if(teacher.getAlias().equals(alias)){
            isTeacherConnect=true;
            return OpCode.Teacher;
        }
        return OpCode.NOT_BELONGS_TO_ROOM;
    }

    public abstract boolean isBelongToRoom(String alias);

    public Response<String> disconnect(String alias) {
        if(teacher.getAlias().equals(alias)){
            isTeacherConnect=false;
        }
        else {
            connectedStudents.remove(alias);
        }
        if(connectedStudents.isEmpty()&&!isTeacherConnect){
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
        waitingForStory=false;
        return true;
    }

    public boolean isTeacherConnect() {
        return isTeacherConnect;
    }

    public void addOpenAnswer(OpenAnswer openAnswer) {
        resolveMission(openAnswer.getMissionId()); // run over current answer
        openAnswers.add(openAnswer);
    }

    public boolean isMissionExists(String missionId) {
        return roomTemplate.getMission(missionId) != null;
    }

    public boolean containsMission(String missionId) {
        return roomTemplate.getMission(missionId) != null;
    }

    public boolean isMissionInCharge(String alias) {
        return alias.equals(missionIncharge);
    }

    public boolean resolveMission(String misId) {
        return openAnswers.removeIf(ans -> ans.getMissionId().equals(misId));
    }

    public abstract Set<String> getStudentsAlias();

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
