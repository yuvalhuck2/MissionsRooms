package Domain.missions;

import DataAPI.RoomType;
import DataAPI.SolutionData;
import Domain.Mission;

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
}
