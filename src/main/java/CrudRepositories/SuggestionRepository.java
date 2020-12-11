package CrudRepositories;

import Domain.Suggestion;
import org.springframework.data.repository.CrudRepository;

public interface SuggestionRepository extends CrudRepository<Suggestion, String> {
}
