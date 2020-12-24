package CrudRepositories;

import missions.room.Domain.IT;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITCrudRepository extends CrudRepository<IT,String> {

}
