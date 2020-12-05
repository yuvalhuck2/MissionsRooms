package Domain.Repositories;

import Domain.Suggestion;
import org.springframework.data.repository.CrudRepository;

public interface SuggestionRepository extends CrudRepository<Suggestion, String> {
}
