package Domain.Repositories;

import Domain.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {
}
