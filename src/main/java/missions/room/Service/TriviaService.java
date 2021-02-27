package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import DataAPI.TriviaQuestionData;
import missions.room.Domain.TriviaQuestion;
import missions.room.Managers.TriviaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class TriviaService {

    @Autowired
    TriviaManager triviaManager;
    /**
     * req 4.6 - create trivia subject
     * @param apiKey - authentication object
     * @param subject - the subject of the trivia the teacher want to add
     * @return if the subject was added successfully
     */
    public Response<Boolean> createTriviaSubject(String apiKey, String subject){

        return triviaManager.createTriviaSubject(apiKey, subject);
    }


    /**
     * req 4.7 - add trivia question
     * @param apiKey - authentication object
     * @param question - question details
     * @return if the question was added successfully
     */
    public Response<Boolean> addTriviaQuestion(String apiKey, TriviaQuestionData question){

        return triviaManager.addTriviaQuestion(apiKey, question);
    }
}
