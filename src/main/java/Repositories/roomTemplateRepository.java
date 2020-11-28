package Repositories;

import Domain.Room;
import Domain.RoomTemplate;
import org.springframework.data.repository.CrudRepository;

public interface roomTemplateRepository extends CrudRepository<RoomTemplate, String> {
}
