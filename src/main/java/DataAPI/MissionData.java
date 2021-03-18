package DataAPI;

import java.util.List;
import java.util.Objects;
import java.util.Set;


public class MissionData {

    private String missionId;
    private Set<RoomType> missionTypes;
    private String name;
    private List<String> question;
    private List<String> answers;
    private String story;
    private List<List<String>> possibleAnswers;
    private double correctPercentage;


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
    }

    public MissionData(String missionId, Set<RoomType> missionTypes, String name, List<String> question, int timeForAns) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
        this.name = name;
        this.question = question;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getStory() {
        return story;
    }

    public List<List<String>> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<List<String>> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public void setCorrectPercentage(double correctPercentage) {
        this.correctPercentage = correctPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionData missionData = (MissionData) o;
        return  Objects.equals(name, missionData.name) &&
                Objects.equals(missionTypes,missionData.missionTypes) &&
                Objects.equals(question,missionData.question) &&
                Objects.equals(answers,missionData.answers);
    }

    public void setStory(String story) {
        this.story=story;
    }
}
