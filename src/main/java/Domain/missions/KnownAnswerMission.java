package Domain.missions;

import DataAPI.RoomType;
import Domain.Mission;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class KnownAnswerMission extends Mission {

    private String question;

    private String realAnswer;

    public KnownAnswerMission() {
    }

    public KnownAnswerMission(String missionId, Set<RoomType> missionTypes, String question, String realAnswer) {
        super(missionId, missionTypes);
        this.question = question;
        this.realAnswer = realAnswer;
    }
}
