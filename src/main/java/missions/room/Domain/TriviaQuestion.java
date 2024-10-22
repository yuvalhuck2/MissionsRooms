package missions.room.Domain;

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

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name="TriviaMissionQuestions",
//            joinColumns ={@JoinColumn(name = "missionId")},
//            inverseJoinColumns={@JoinColumn(name="id")}
//    )
//    private List<TriviaMission> triviaMissions;

    private String correctAnswer;

    @ManyToOne
    @OnDelete(action =  OnDeleteAction.NO_ACTION)
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

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

}
