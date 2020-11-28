package Repositories;

import Domain.Room;
import Domain.Pack_Try.User;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {
}
