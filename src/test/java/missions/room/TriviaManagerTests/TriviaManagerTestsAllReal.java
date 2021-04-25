package missions.room.TriviaManagerTests;

import Data.Data;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.TriviaQuestionData;
import missions.room.Domain.TriviaQuestion;
import missions.room.Managers.SuggestionManager;
import missions.room.Managers.TriviaManager;
import missions.room.Repo.TriviaRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TriviaManagerTestsAllReal extends TriviaManagerTestsRealRamTeacher{

    @Autowired
    private TriviaRepo realTriviaRepo;

    @Override
    protected void initMocks() {
        super.initMocks();
        try {
            Field triviaRepo = TriviaManager.class.getDeclaredField("triviaRepo");
            triviaRepo.setAccessible(true);
            triviaRepo.set(triviaManager,realTriviaRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void addTriviaQuestionFailSubjectDoesntExist(){
        Response<Boolean> res = triviaManager.addTriviaQuestion(teacherApiKey, dataGenerator.getTriviaQuestion(Data.VALID2));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.SUBJECT_DOESNT_EXIST);
    }

    @Test
    public void deleteTriviaQuestionFailSubjectDoesntExist(){
        Response<Boolean> res = triviaManager.deleteTriviaQuestion(teacherApiKey, dataGenerator.getTriviaQuestion(Data.VALID2));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.SUBJECT_DOESNT_EXIST);
    }


    @Test
    public void addTriviaSubjectFailSubjectAlreadyExist(){
        addTriviaSubject(dataGenerator.getTriviaSubject(Data.VALID));
        Response<Boolean> res = triviaManager.createTriviaSubject(teacherApiKey, dataGenerator.getTriviaSubject(Data.VALID));
        assertFalse(res.getValue());
        assertEquals(res.getReason(), OpCode.Trivia_Subject_Already_Exists);
    }

    @Test
    public void getAllQuestionInSubjectNotExistSubjectSuccess(){
        addTriviaQuestionSuccess();
        Response<List<TriviaQuestionData>> res = triviaManager.GetAllQuestionsBySubject(teacherApiKey, dataGenerator.getTriviaSubject(Data.VALID3));
        assertEquals(0, res.getValue().size());
    }

    @Test
    public void getAllQuestionInSubjectExistSubjectSuccess(){
        addTriviaQuestionSuccess();
        Response<List<TriviaQuestionData>> res = triviaManager.GetAllQuestionsBySubject(teacherApiKey, dataGenerator.getTriviaSubject(Data.VALID));
        assertTrue(res.getValue().size() > 0);
    }

    @Test
    public void getAllQuestionsSuccess(){
        addTriviaQuestionSuccess();
        Response<List<TriviaQuestionData>> res = triviaManager.getTriviaQuestions(teacherApiKey);
        assertEquals(res.getValue().get(0), dataGenerator.getTriviaQuestion(Data.VALID));
    }
}
