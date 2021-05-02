package missions.room.Managers;

import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.APIObjects.TriviaQuestionData;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import missions.room.Domain.*;
import missions.room.Domain.Users.Teacher;
import missions.room.Repo.TriviaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriviaManager extends TeacherManager{

    @Autowired
    private TriviaRepo triviaRepo;


    private OpCode checkAddSubjectArgValidity(String apiKey, String subject){
        if(!Utils.checkString(subject)){
            return OpCode.Invalid_Trivia_Subject;
        }
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!= OpCode.Success){
            return teacherResponse.getReason();
        }
        return OpCode.Success;
    }

    private OpCode checkAddQuestionArgValidity(String apiKey,TriviaQuestionData question) {
        if (!Utils.checkString(question.getQuestion()) || !Utils.checkStringArray(question.getAnswers())
            || !Utils.checkString((question.getCorrectAnswer())) || !Utils.checkString((question.getSubject()))) {
            return OpCode.Invalid_Trivia_Question;
        }
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!= OpCode.Success){
            return teacherResponse.getReason();
        }
        return OpCode.Success;
    }

    /**
     * req 4.6 - create trivia subject
     * @param apiKey - authentication object
     * @param subject - the subject of the trivia the teacher want to add
     * @return if the subject was added successfully
     */
    public Response<Boolean> createTriviaSubject(String apiKey, String subject){
        OpCode validity = checkAddSubjectArgValidity(apiKey, subject);
        if(validity != OpCode.Success){
            return new Response<>(false
                    ,validity);
        }
        TriviaSubject triviaSubject = new TriviaSubject(subject);
        OpCode saveAnswer=triviaRepo.addTriviaSubject(triviaSubject);
        return new Response<>(saveAnswer==OpCode.Success
                ,saveAnswer);
    }


    /**
     * req 4.7 - add trivia question
     * @param apiKey - authentication object
     * @param question - question details
     * @return if the question was added successfully
     */
    public Response<Boolean> addTriviaQuestion(String apiKey, TriviaQuestionData question){
        OpCode validity = checkAddQuestionArgValidity(apiKey, question);
        if(validity != OpCode.Success){
            return new Response<>(false
                    ,validity);
        }
        if (!triviaRepo.isSubjectExist(question.getSubject())) {
            return new Response<>(false, OpCode.SUBJECT_DOESNT_EXIST);
        }
        TriviaQuestion triviaQuestion = new TriviaQuestion(
                UniqueStringGenerator.getTimeNameCode("TRIV"),
                question.getQuestion(), question.getAnswers(), question.getCorrectAnswer(), question.getSubject());

        OpCode saveAnswer= triviaRepo.addTriviaQuestion(triviaQuestion);
        return new Response<>(saveAnswer==OpCode.Success
                ,saveAnswer);
    }

    public Response<List<TriviaQuestion>> GetAllQuestionsBySubject(String apiKey, String subjectName) {
        OpCode validity = checkAddSubjectArgValidity(apiKey, subjectName);
        if(validity != OpCode.Success){
            return new Response<>(null
                    ,validity);
        }
        return triviaRepo.getAllQuestionsBySubject(subjectName);
    }
}
