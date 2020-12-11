package Domain.missions;


import DataAPI.RoomType;
import Domain.Mission;
import Domain.TriviaQuestion;

import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import java.util.Map;
import java.util.Set;

@Entity
public class TriviaMission extends Mission {

    private int secondsForAnswer;

    @OneToMany
    @MapKey(name = "id")
    private Map<String, TriviaQuestion> questions;

    public TriviaMission() {
    }

    public TriviaMission(String missionId, Set<RoomType> missionTypes, int secondsForAnswer, Map<String, TriviaQuestion> questions) {
        super(missionId, missionTypes);
        this.secondsForAnswer = secondsForAnswer;
        this.questions = questions;
    }
}
