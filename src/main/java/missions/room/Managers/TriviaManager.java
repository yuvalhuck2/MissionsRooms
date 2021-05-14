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
import java.util.stream.Collectors;

@Service
public class TriviaManager extends TeacherManager{

    @Autowired
    private TriviaRepo triviaRepo;


    private OpCode checkSubjectArgValidity(String apiKey, String subject){
        if(!Utils.checkString(subject)){
            return OpCode.Invalid_Trivia_Subject;
        }
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!= OpCode.Success){
            return teacherResponse.getReason();
        }
        return OpCode.Success;
    }

    private OpCode checkQuestionArgValidity(String apiKey, TriviaQuestionData question) {
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
        OpCode validity = checkSubjectArgValidity(apiKey, subject);
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
        OpCode validity = checkQuestionArgValidity(apiKey, question);
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

    public Response<List<TriviaQuestionData>> GetAllQuestionsBySubject(String apiKey, String subjectName) {
        OpCode validity = checkSubjectArgValidity(apiKey, subjectName);
        if(validity != OpCode.Success){
            return new Response<>(null
                    ,validity);
        }
        Response<List<TriviaQuestion>> res = triviaRepo.getAllQuestionsBySubject(subjectName);

        if(res.getReason() == OpCode.Success){
            List<TriviaQuestion> triviaQuestions = res.getValue();
            List<TriviaQuestionData> triviaQuestionData = triviaQuestions.stream()
                    .map(tq -> new TriviaQuestionData(tq.getId(), tq.getQuestion(), tq.getAnswers(), tq.getCorrectAnswer(), tq.getSubject()))
                    .collect(Collectors.toList());
            return new Response<>(triviaQuestionData, OpCode.Success);
        }else{
            return new Response<>(null, res.getReason());
        }
    }


    public Response<Boolean> deleteTriviaQuestion(String apiKey, String id){
        if(!Utils.checkString(id)){
            return new Response<>(false , OpCode.Invalid_Trivia_Question);
        }
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(false, teacherResponse.getReason());
        }
        OpCode saveAnswer= triviaRepo.deleteTriviaQuestion(id);
        return new Response<>(saveAnswer==OpCode.Success
                ,saveAnswer);
    }

    public Response<List<TriviaQuestionData>> getTriviaQuestions(String apiKey){
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(null, teacherResponse.getReason());
        }
        Response<List<TriviaQuestion>> triviaQuestions = triviaRepo.getTriviaQuestions();
        if (triviaQuestions.getReason() == OpCode.Success){
            List<TriviaQuestion> trivQuestions = triviaQuestions.getValue();
            List<TriviaQuestionData> triviaQuestionData = trivQuestions.stream()
                    .map(tq -> new TriviaQuestionData(tq.getId(), tq.getQuestion(), tq.getAnswers(), tq.getCorrectAnswer(), tq.getSubject()))
                    .collect(Collectors.toList());
            return new Response<>(triviaQuestionData, OpCode.Success);
        }else{
            return new Response<>(null, triviaQuestions.getReason());
        }
    }
}
