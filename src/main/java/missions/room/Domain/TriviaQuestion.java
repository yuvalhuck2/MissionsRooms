package missions.room.Domain;

import missions.room.Domain.missions.TriviaMission;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="TriviaMissionQuestions",
            joinColumns ={@JoinColumn(name = "missionId")},
            inverseJoinColumns={@JoinColumn(name="id")}
    )
    private List<TriviaMission> triviaMissions;

    private String correctAnswer;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action =  OnDeleteAction.CASCADE)
    @JoinColumn(name="subject", referencedColumnName = "name", nullable=false)
    private TriviaSubject subject;

    public TriviaQuestion() {
    }

    public TriviaQuestion(String id, String question, List<String> answers, String correctAnswer, String subject) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.subject = new TriviaSubject(subject);
    }

    public String getSubject() {
        return subject.getName();
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
