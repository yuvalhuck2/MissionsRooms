package missions.room.Domain.missions;

import DataAPI.RoomType;
import DataAPI.SolutionData;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

@Entity
public class OpenAnswerMission extends Mission {

    private String question;

    @Transient
    private SolutionData solution;

    public OpenAnswerMission(String missionId, Set<RoomType> missionType, String question) {
        super(missionId, missionType);
        this.question=question;
    }

    public OpenAnswerMission() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public SolutionData getSolution() {
        return solution;
    }

    public void setSolution(SolutionData solution) {
        this.solution = solution;
    }
}
