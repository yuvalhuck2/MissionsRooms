package missions.room.Domain;

import missions.room.Domain.missions.TriviaMission;

import javax.persistence.*;
import java.util.List;

@Entity
public class TriviaQuestion {

    @Id
    private String id;

    private String question;

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name="INDEX")
    @Column(name="answer")
    private List<String> answers;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="TriviaMissionQuestions",
            joinColumns ={@JoinColumn(name = "missionId")},
            inverseJoinColumns={@JoinColumn(name="id")}
    )
    private List<TriviaMission> triviaMissions;

    private int correctAnswer;

    public TriviaQuestion() {
    }

    public TriviaQuestion(String id, String question, List<String> answers, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<TriviaMission> getTriviaMissions() {
        return triviaMissions;
    }

    public void setTriviaMissions(List<TriviaMission> triviaMissions) {
        this.triviaMissions = triviaMissions;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
