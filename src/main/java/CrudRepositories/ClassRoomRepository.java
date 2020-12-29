package CrudRepositories;

import missions.room.Domain.Classroom;
import org.springframework.data.repository.CrudRepository;

public interface ClassRoomRepository extends CrudRepository<Classroom, String> {
}
