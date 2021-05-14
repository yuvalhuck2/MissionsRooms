package DataObjects.APIObjects;

import java.util.List;
import java.util.Objects;

public class TriviaQuestionData {
    private String id;
    private String question;
    private List<String> wrongAnswers;
    private String correctAnswer;
    private String subject;

    public TriviaQuestionData(String id, String question, List<String> answers, String correctAnswer, String subject) {
        this.id = id;
        this.question = question;
        this.wrongAnswers = answers;
        this.correctAnswer = correctAnswer;
        this.subject = subject;
    }

    public String getId(){return id;}

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return wrongAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriviaQuestionData that = (TriviaQuestionData) o;
        return Objects.equals(question, that.question) && Objects.equals(wrongAnswers, that.wrongAnswers) && Objects.equals(correctAnswer, that.correctAnswer) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, wrongAnswers, correctAnswer, subject);
    }

}
