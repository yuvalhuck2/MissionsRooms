package missions.room.TriviaManagerTests;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.TriviaQuestionRepository;
import CrudRepositories.TriviaSubjectRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.APIObjects.TriviaQuestionData;
import missions.room.Domain.TriviaQuestion;
import missions.room.Domain.TriviaSubject;
import missions.room.Managers.TriviaManager;
import missions.room.Repo.TriviaRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;

import static Data.Data.TRIVIA_VALID;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TriviaManagerTestsAllReal extends TriviaManagerTestsRealRamTeacher{

    @Autowired
    private TriviaRepo realTriviaRepo;

    @Autowired
    private TriviaSubjectRepository triviaSubjectRepository;

    @Autowired
    private TriviaQuestionRepository realTriviaCrudRepository;

    @Autowired
    private MissionCrudRepository missionCrudRepository;

    @Override
    protected void initMocks(TriviaQuestionData triviaQuestion) {
        super.initMocks(triviaQuestion);
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

    @Test
    @Override
    public void deleteTriviaQuestionHappyCase() {
        setUpDeleteTriviaQuestion();
        super.deleteTriviaQuestionHappyCase();
        assertFalse(realTriviaCrudRepository.existsById(questionId));
    }

    private void setUpDeleteTriviaQuestion() {
        triviaManager.createTriviaSubject(teacherApiKey, dataGenerator.getTriviaSubject(Data.VALID));
        addTriviaQuestion(dataGenerator.getTriviaQuestion(Data.VALID));
        questionId = triviaManager.getTriviaQuestions(teacherApiKey).getValue().get(0).getId();
    }

    @Test
    @Override
    void testDeleteSuggestionInvalidHasMissionWithQuestion() {
        setUpDeleteTriviaQuestion();
        addTriviaQuestion(dataGenerator.getTriviaQuestion(Data.VALID2));
        missionCrudRepository.save(dataGenerator.getMission(TRIVIA_VALID));
        super.testDeleteSuggestionInvalidHasMissionWithQuestion();
        missionCrudRepository.deleteAll();
    }

    private void addTriviaQuestion(TriviaQuestionData triviaQuestion) {
        triviaSubjectRepository.save(new TriviaSubject(triviaQuestion.getSubject()));
        realTriviaCrudRepository.save(new TriviaQuestion(triviaQuestion.getId(),
                triviaQuestion.getQuestion(),
                triviaQuestion.getAnswers(),
                triviaQuestion.getCorrectAnswer(),
                triviaQuestion.getSubject()));
    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
        realTriviaCrudRepository.deleteAll();
        triviaSubjectRepository.deleteAll();
    }
}
