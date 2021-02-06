package CrudRepositories;

import missions.room.Domain.Suggestion;
import org.springframework.data.repository.CrudRepository;

public interface SuggestionCrudRepository extends CrudRepository<Suggestion, String> {
}
