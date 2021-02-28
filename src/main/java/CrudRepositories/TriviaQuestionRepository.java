package CrudRepositories;

import missions.room.Domain.TriviaQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TriviaQuestionRepository extends CrudRepository<TriviaQuestion, String> {
    public List<TriviaQuestion> findBySubjectName(String subjectName);
}
