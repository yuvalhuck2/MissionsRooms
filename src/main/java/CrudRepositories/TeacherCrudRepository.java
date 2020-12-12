package CrudRepositories;

import Domain.SchoolUser;
import Domain.Teacher;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface TeacherCrudRepository extends CrudRepository<Teacher,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Teacher a where a.alias = :alias")
    Teacher findTeacherForWrite(@Param("alias") String alias);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Teacher a where a.alias = :alias")
    Teacher findTeacherForRead(@Param("alias") String alias);
}
