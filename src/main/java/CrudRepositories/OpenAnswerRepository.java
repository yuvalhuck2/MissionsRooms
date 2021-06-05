package CrudRepositories;

import missions.room.Domain.OpenAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OpenAnswerRepository extends CrudRepository<OpenAnswer, String> {

}
