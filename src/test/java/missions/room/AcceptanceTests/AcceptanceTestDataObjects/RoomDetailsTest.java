package missions.room.AcceptanceTests.AcceptanceTestDataObjects;
import java.util.Objects;

public class RoomDetailsTest {

    private MissionDetailsTest currentMission;
    private String roomName;
    private int bonus;
    private ParticipantTypeTest participantType;
    private String participant;
    private int numberOfMissions;
    private int currentMissionNumber;

    public RoomDetailsTest(String roomName, int bonus, String participant, ParticipantTypeTest participantType) {
        this.roomName = roomName;
        this.bonus = bonus;
        this.participant = participant;
        this. participantType = participantType;
        this.currentMissionNumber = 0;
    }

    public RoomDetailsTest(MissionDetailsTest currentMission, String roomName, ParticipantTypeTest participantType, int numberOfMissions, int currentMissionNumber) {
        this.currentMission = currentMission;
        this.roomName = roomName;
        this.participantType = participantType;
        this.numberOfMissions = numberOfMissions;
        this.currentMissionNumber = currentMissionNumber;
    }

    public RoomDetailsTest(RoomDetailsTest roomDetailsTest) {
        this.roomName = roomDetailsTest.getRoomName();
        this.bonus = roomDetailsTest.getBonus();
        this.participant = roomDetailsTest.getParticipant();
        this. participantType = roomDetailsTest.getParticipantType();
        this.currentMissionNumber = roomDetailsTest.getCurrentMissionNumber();
    }

    public MissionDetailsTest getCurrentMission() {
        return currentMission;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getBonus() {
        return bonus;
    }

    public ParticipantTypeTest getParticipantType() {
        return participantType;
    }

    public String getParticipant() {
        return participant;
    }

    public int getNumberOfMissions() {
        return numberOfMissions;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setParticipantType(ParticipantTypeTest participantType) {
        this.participantType = participantType;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public int getCurrentMissionNumber() {
        return currentMissionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDetailsTest that = (RoomDetailsTest) o;
        return currentMissionNumber == that.currentMissionNumber &&
                roomName.equals(that.roomName) &&
                participantType == that.participantType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName, participantType, participant, currentMissionNumber);
    }
}
