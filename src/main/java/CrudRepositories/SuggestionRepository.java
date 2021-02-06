package CrudRepositories;

import missions.room.Domain.Suggestion;
import org.springframework.data.repository.CrudRepository;

public interface SuggestionRepository extends CrudRepository<Suggestion, String> {
}
