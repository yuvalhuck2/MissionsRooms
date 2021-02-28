package missions.room.Domain;

import ExternalSystems.UniqueStringGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OpenAnswer {

    @Id
    private String id;
    private String roomId;
    private String missionId;
    private byte[] file;
    private String openAnswerText;

    public OpenAnswer(String roomId, String missionId, byte[] file, String openAnswerText) {
        this.id = UniqueStringGenerator.getTimeNameCode("OPEN_ANS");
        this.roomId = roomId;
        this.missionId = missionId;
        this.file = file;
        this.openAnswerText = openAnswerText;
    }

    public OpenAnswer() {

    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getOpenAnswerText() {
        return openAnswerText;
    }

    public void setOpenAnswerText(String openAnswerText) {
        this.openAnswerText = openAnswerText;
    }
}
