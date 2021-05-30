package CrudRepositories;

import missions.room.Domain.TriviaQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TriviaQuestionRepository extends CrudRepository<TriviaQuestion, String> {
    @Query("SELECT t FROM TriviaQuestion t JOIN t.subject ts WHERE ts.name = :subjectName")
    List<TriviaQuestion> findBySubjectName(@Param("subjectName") String subjectName);

}
