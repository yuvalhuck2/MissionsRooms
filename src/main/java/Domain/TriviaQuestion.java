package Domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TriviaQuestion {

    @Id
    private String id;

    private String question;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="answer")
    private Set<String> answers;

    private String correctAnswer;

    public TriviaQuestion() {
    }

    public TriviaQuestion(String id, String question, Set<String> answers, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
