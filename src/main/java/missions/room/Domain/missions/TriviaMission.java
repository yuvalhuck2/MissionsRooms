package missions.room.Domain.missions;


import DataAPI.RoomType;
import missions.room.Domain.Mission;
import missions.room.Domain.TriviaQuestion;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
public class TriviaMission extends Mission {

    private int secondsForAnswer;

    @OneToMany
    @MapKey(name = "id")
    @JoinTable(name="TriviaMissionQuestions",
            joinColumns ={@JoinColumn(name = "missionId")},
            inverseJoinColumns={@JoinColumn(name="id")}
    )
    private Map<String, TriviaQuestion> questions;

    public TriviaMission() {
    }

    public TriviaMission(String missionId, Set<RoomType> missionTypes, int secondsForAnswer, Map<String, TriviaQuestion> questions) {
        super(missionId, missionTypes);
        this.secondsForAnswer = secondsForAnswer;
        this.questions = questions;
    }
}
