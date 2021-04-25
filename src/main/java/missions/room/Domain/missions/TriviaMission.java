package missions.room.Domain.missions;


import DataAPI.MissionData;
import DataAPI.RoomType;
import missions.room.Domain.TriviaQuestion;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
public class TriviaMission extends Mission {

    private double passRatio;

    private final static String missionName = "Trivia mission";

    @OneToMany
    @MapKey(name = "id")
    @JoinTable(name="TriviaMissionQuestions",
            joinColumns ={@JoinColumn(name = "missionId")},
            inverseJoinColumns={@JoinColumn(name="id")}
    )
    private Map<String, TriviaQuestion> questions;

    public TriviaMission() {
    }

    public TriviaMission(String missionId, Set<RoomType> missionTypes, double passRatio, Map<String, TriviaQuestion> questions) {
        super(missionId, missionTypes);
        this.passRatio = passRatio;
        this.questions = questions;
    }

    public double getPassRatio() {
        return passRatio;
    }

    public void setPassRatio(double passRatio) {
        this.passRatio = passRatio;
    }

    public Map<String, TriviaQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, TriviaQuestion> questions) {
        this.questions = questions;
    }

    @Override
    protected MissionData completeTheRestOfMissionData(MissionData missionData) {
        missionData.setTriviaQuestionMap(this.questions);
        missionData.setPassRatio(this.passRatio);
        return missionData;
    }

    @Override
    public String getMissionName() {
        return missionName;
    }
}
