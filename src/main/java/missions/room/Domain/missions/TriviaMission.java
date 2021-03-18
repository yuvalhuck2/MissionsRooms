package missions.room.Domain.missions;


import DataAPI.MissionData;
import DataAPI.RoomType;
import missions.room.Domain.TriviaQuestion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class TriviaMission extends Mission {

    private int secondsForAnswer;
    private double correctPercentage;

    @OneToMany
    @MapKey(name = "id")
    @JoinTable(name="TriviaMissionQuestions",
            joinColumns ={@JoinColumn(name = "missionId")},
            inverseJoinColumns={@JoinColumn(name="id")}
    )
    private Map<String, TriviaQuestion> questions;

    private final static String missionName = "Trivia_Mission";

    public TriviaMission() {
    }

    public TriviaMission(String missionId, Set<RoomType> missionTypes, int secondsForAnswer, double correctPercentage, Map<String, TriviaQuestion> questions) {
        super(missionId, missionTypes);
        this.secondsForAnswer = secondsForAnswer;
        this.correctPercentage = correctPercentage;
        this.questions = questions;
    }

    public int getSecondsForAnswer() {
        return secondsForAnswer;
    }

    public void setSecondsForAnswer(int secondsForAnswer) {
        this.secondsForAnswer = secondsForAnswer;
    }

    public Map<String, TriviaQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, TriviaQuestion> questions) {
        this.questions = questions;
    }

    public List<List<String>> getPossibleAnswers(){
        List<List<String>> possibleAnswers = new ArrayList<>();

        for(TriviaQuestion triviaQuestion : this.questions.values()){
            List<String> currentAnswers = triviaQuestion.getAnswers();
            possibleAnswers.add(currentAnswers);
        }
        return possibleAnswers;
    }

    public List<String> getTriviaCorrectAnswers(){
        List<String> answers = new ArrayList<>();
        for (TriviaQuestion triviaQuestion : this.questions.values()){
            answers.add(triviaQuestion.getCorrectAnswer());
        }
        return answers;
    }

    public List<String> getTriviaQuestions(){
        List<String> questions = new ArrayList<>();
        for (TriviaQuestion triviaQuestion : this.questions.values()){
            questions.add(triviaQuestion.getQuestion());
        }
        return questions;
    }

    @Override
    public String getMissionName() {
        return missionName;
    }

    @Override
    public MissionData completeTheRestOfMissionData(MissionData missionData) {
        List<String> questions = this.getTriviaQuestions();
        List<String> answers = this.getTriviaCorrectAnswers();
        List<List<String>> possibleAnswers = this.getPossibleAnswers();

        missionData.setQuestion(questions);
        missionData.setAnswers(answers);
        missionData.setPossibleAnswers(possibleAnswers);
        missionData.setCorrectPercentage(this.correctPercentage);

        return missionData;
    }
}
