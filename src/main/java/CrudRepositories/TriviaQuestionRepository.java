package CrudRepositories;

import missions.room.Domain.TriviaQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TriviaQuestionRepository extends CrudRepository<TriviaQuestion, String> {
//    @Query("SELECT t FROM TriviaQuestion t INNER JOIN TriviaSubject ts WHERE ts.name = ?1")
    List<TriviaQuestion> findBySubjectName(String subjectName);
}
