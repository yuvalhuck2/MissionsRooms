package DataAPI;


import java.util.Objects;

//TODO implement needs to only the current mission and not all the missions
public class RoomDetailsData {
    private String roomId;
    private String name;
    private MissionData currentMission;
    private RoomType roomType;
    private boolean waitingForStory;
    private int currentMissionNumber;
    private int numberOfMissions;
    private String studentName;
    public RoomDetailsData(String roomId, String name, MissionData currentMission, RoomType roomType, boolean waitingForStory){
        this.roomId=roomId;
        this.name=name;
        this.currentMission=currentMission;
        this.roomType=roomType;
        this.waitingForStory=waitingForStory;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MissionData getCurrentMission() {
        return currentMission;
    }

    public void setCurrentMission(MissionData currentMission) {
        this.currentMission = currentMission;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public boolean isWaitingForStory() {
        return waitingForStory;
    }

    public int getCurrentMissionNumber() {
        return currentMissionNumber;
    }

    public void setCurrentMissionNumber(int currentMissionNumber) {
        this.currentMissionNumber = currentMissionNumber;
    }

    public int getNumberOfMissions() {
        return numberOfMissions;
    }

    public void setNumberOfMissions(int numberOfMissions) {
        this.numberOfMissions = numberOfMissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDetailsData roomDetailsData = (RoomDetailsData) o;
        return  Objects.equals(name, roomDetailsData.name) &&
                Objects.equals(currentMission,roomDetailsData.currentMission) &&
                Objects.equals(roomType,roomDetailsData.roomType);
    }
}
