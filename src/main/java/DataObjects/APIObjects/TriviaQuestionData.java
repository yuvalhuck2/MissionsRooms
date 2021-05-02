package DataObjects.APIObjects;

import java.util.List;

public class TriviaQuestionData {
    private String question;
    private List<String> wrongAnswers;
    private String correctAnswer;
    private String subject;

    public TriviaQuestionData(String question, List<String> answers, String correctAnswer, String subject) {
        this.question = question;
        this.wrongAnswers = answers;
        this.correctAnswer = correctAnswer;
        this.subject = subject;
    }

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
}
