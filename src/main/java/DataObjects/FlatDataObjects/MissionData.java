package DataObjects.FlatDataObjects;

import missions.room.Domain.TriviaQuestion;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class MissionData {

    private String missionId;
    private Set<RoomType> missionTypes;
    private String name;
    private List<String> question;
    private int timeForAns;
    private List<String> answers;
    private String story;
    private double passRatio;
    private Map<String, TriviaQuestion> triviaQuestionMap;

    public MissionData(String missionId, Set<RoomType> missionTypes, String name, String story) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
        this.name = name;
        this.story = story;
    }

    public MissionData(String missionId, Set<RoomType> missionTypes){
        this.missionId=missionId;
        this.missionTypes=missionTypes;
        this.question=null;
        this.timeForAns=-1;
    }

    public MissionData(String missionId, Set<RoomType> missionTypes, String name, List<String> question, int timeForAns) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
        this.name = name;
        this.question = question;
        this.timeForAns = timeForAns;
    }

    public MissionData(String missionId, Set<RoomType> missionTypes, String name, double passRatio, Map<String, TriviaQuestion> triviaQuestionMap){
        this.missionId = missionId;
        this.missionTypes = missionTypes;
        this.name = name;
        this.passRatio = passRatio;
        this.triviaQuestionMap = triviaQuestionMap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    public String getMissionId() {
        return missionId;
    }

    public Set<RoomType> getMissionTypes() {
        return missionTypes;
    }

    public String getName() {
        return name;
    }

    public List<String> getQuestion() {
        return question;
    }

    public int getTimeForAns() {
        return timeForAns;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getStory() {
        return story;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionData missionData = (MissionData) o;
        return  Objects.equals(name, missionData.name) &&
                Objects.equals(missionTypes,missionData.missionTypes) &&
                Objects.equals(question,missionData.question) &&
                Objects.equals(timeForAns,missionData.timeForAns) &&
                Objects.equals(answers,missionData.answers);
    }

    public void setStory(String story) {
        this.story=story;
    }

    public double getPassRatio() {
        return passRatio;
    }

    public void setPassRatio(double passRatio) {
        this.passRatio = passRatio;
    }

    public Map<String, TriviaQuestion> getTriviaQuestionMap() {
        return triviaQuestionMap;
    }

    public void setTriviaQuestionMap(Map<String, TriviaQuestion> triviaQuestionMap) {
        this.triviaQuestionMap = triviaQuestionMap;
    }
}
