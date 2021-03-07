package CrudRepositories;

import missions.room.Domain.Users.Student;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface StudentCrudRepository extends CrudRepository<Student,String> {



    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Student a where a.alias = :alias")
    Student findUserForWrite(@Param("alias") String alias);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Student a where a.alias = :alias")
    Student findUserForRead(@Param("alias") String alias);
}
