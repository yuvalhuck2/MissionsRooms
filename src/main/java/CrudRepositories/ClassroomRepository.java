package CrudRepositories;

import missions.room.Domain.Classroom;
import org.springframework.data.repository.CrudRepository;

public interface ClassroomRepository extends CrudRepository<Classroom,String> {
}
