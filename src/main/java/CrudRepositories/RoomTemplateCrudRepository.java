package CrudRepositories;

import missions.room.Domain.RoomTemplate;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface RoomTemplateCrudRepository extends CrudRepository<RoomTemplate,String> {

}
