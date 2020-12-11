package CrudRepositories;

import Domain.Rooms.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {
}
