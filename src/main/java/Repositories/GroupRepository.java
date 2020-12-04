package Repositories;

import Domain.Room;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Room, String> {
}
