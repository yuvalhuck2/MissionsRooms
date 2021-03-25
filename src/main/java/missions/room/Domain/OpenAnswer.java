package missions.room.Domain;

import ExternalSystems.UniqueStringGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class OpenAnswer {

    @Id
    private String id;
    private String missionId;
    private String openAnswerText;
    private boolean hasFile;
    @Transient
    private MultipartFile file;

    public OpenAnswer(String missionId, String openAnswerText, MultipartFile file) {
        this.id = UniqueStringGenerator.getTimeNameCode("OPEN_ANS");
        this.missionId = missionId;
        this.openAnswerText = openAnswerText;
        this.file = file;
        this.hasFile = file != null;
    }

    public OpenAnswer() {

    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getOpenAnswerText() {
        return openAnswerText;
    }

    public void setOpenAnswerText(String openAnswerText) {
        this.openAnswerText = openAnswerText;
    }

    public boolean isHasFile() {
        return hasFile;
    }
}
