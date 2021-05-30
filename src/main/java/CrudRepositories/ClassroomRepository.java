package CrudRepositories;

import missions.room.Domain.Classroom;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface ClassroomRepository extends CrudRepository<Classroom, String> {

    @Query("select distinct cr FROM Classroom cr " +
            "join cr.classGroups g " +
            "join g.students s " +
            "where s.alias= :student")
    Classroom findClassroomByStudent(String student);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Classroom a where a.className = :className")
    Classroom findClassroomForWrite(@Param("className") String className);

    @Query("select className from Classroom")
    Iterable<String> getAllNames();

    @Query("select a from Classroom a where a.className = :className")
    Classroom findClassroom(@Param("className") String className);

    @Query("select a from Classroom a where a.className like :grade%")
    List<Classroom> findClassroomByGrade(@Param("grade") String grade);
}
