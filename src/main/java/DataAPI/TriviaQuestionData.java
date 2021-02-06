package DataAPI;

import java.util.List;

public class TriviaQuestionData {
    private String question;
    private List<String> answers;
    private int numberOfCorrectAnswer;
    private String subject;

    public TriviaQuestionData(String question, List<String> answers, int numberOfCorrectAnswer, String subject) {
        this.question = question;
        this.answers = answers;
        this.numberOfCorrectAnswer = numberOfCorrectAnswer;
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getNumberOfCorrectAnswer() {
        return numberOfCorrectAnswer;
    }

    public String getSubject() {
        return subject;
    }
}
