package Repositories;

import Domain.Room;
import Domain.RoomTemplate;
import org.springframework.data.repository.CrudRepository;

public interface RoomTemplateRepository extends CrudRepository<RoomTemplate, String> {
}
