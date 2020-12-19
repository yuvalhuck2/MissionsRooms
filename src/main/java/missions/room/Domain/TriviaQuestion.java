package missions.room.Domain;

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

    private int correctAnswer;

    public TriviaQuestion() {
    }

    public TriviaQuestion(String id, String question, List<String> answers, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
