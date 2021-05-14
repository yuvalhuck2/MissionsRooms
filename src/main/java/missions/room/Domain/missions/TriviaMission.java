package missions.room.Domain.missions;


import DataObjects.FlatDataObjects.RoomType;
import DataObjects.FlatDataObjects.MissionData;
import missions.room.Domain.TriviaQuestion;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        missionData.setQuestion(this.questions
                .values()
                .stream()
                .map(TriviaQuestion::getQuestion)
                .collect(Collectors.toList()));
        return missionData;
    }

    @Override
    public String getMissionName() {
        return missionName;
    }
}
