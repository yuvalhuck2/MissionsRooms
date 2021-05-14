package missions.room.Repo;

import CrudRepositories.TriviaQuestionRepository;
import CrudRepositories.TriviaSubjectRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.TriviaQuestion;
import missions.room.Domain.TriviaSubject;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriviaRepo {

    @Autowired
    TriviaSubjectRepository triviaSubjectRepository;

    @Autowired
    TriviaQuestionRepository triviaQuestionRepository;

    public OpCode addTriviaSubject(TriviaSubject triviaSubject) {
        try{
            boolean isSubjectExist = isSubjectExist(triviaSubject.getName());
            if(isSubjectExist){
                return OpCode.Trivia_Subject_Already_Exists;
            }
            triviaSubjectRepository.save(triviaSubject);
            return OpCode.Success;
        } catch (Exception e){
            return OpCode.DB_Error;
        }
    }

    public OpCode deleteTriviaSubject(TriviaSubject triviaSubject) {
        try{
            boolean isSubjectExist = isSubjectExist(triviaSubject.getName());
            if(!isSubjectExist)
                return OpCode.Trivia_Subject_Not_Exists;
            triviaSubjectRepository.delete(triviaSubject);
            return OpCode.Success;
        }catch(Exception e){
            return OpCode.DB_Error;
        }
    }

    public boolean isSubjectExist(String subject) {
        return triviaSubjectRepository.existsById(subject);
    }

    public OpCode addTriviaQuestion(TriviaQuestion triviaQuestion) {
        try{
            triviaQuestionRepository.save(triviaQuestion);
            return OpCode.Success;
        } catch(Exception e) {
            return OpCode.DB_Error;
        }
    }

    public OpCode deleteTriviaQuestion(TriviaQuestion triviaQuestion) {
        try{
            triviaQuestionRepository.delete(triviaQuestion);
            return OpCode.Success;
        }catch(Exception e){
            return OpCode.DB_Error;
        }
    }

    public Response<List<TriviaQuestion>> getAllQuestionsBySubject(String subjectName) {
        try{
            List<TriviaQuestion> questions = triviaQuestionRepository.findBySubjectName(subjectName);
            return new Response<>(questions, OpCode.Success);
        } catch(Exception e) {
            return new Response<>(null, OpCode.DB_Error);
        }
    }

    public Response<List<TriviaQuestion>> getTriviaQuestions() {
        try{
            List<TriviaQuestion> questions = Lists.newArrayList(triviaQuestionRepository.findAll());
            return new Response<>(questions, OpCode.Success);
        } catch(Exception e) {
            return new Response<>(null, OpCode.DB_Error);
        }
    }
}
