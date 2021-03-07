package CrudRepositories;

import missions.room.Domain.TriviaSubject;
import org.springframework.data.repository.CrudRepository;

public interface TriviaSubjectRepository extends CrudRepository<TriviaSubject, String> {
}
