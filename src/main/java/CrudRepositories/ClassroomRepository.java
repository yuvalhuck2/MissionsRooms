package CrudRepositories;

import missions.room.Domain.Classroom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClassroomRepository extends CrudRepository<Classroom, String> {

    @Query("select distinct cr FROM Classroom cr " +
            "join cr.classGroups g " +
            "join g.students s " +
            "where s.alias= :student")
    Classroom findClassroomByStudent(String student);
}
