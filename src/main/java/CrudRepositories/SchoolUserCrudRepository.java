package CrudRepositories;

import missions.room.Domain.Users.SchoolUser;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface SchoolUserCrudRepository extends CrudRepository<SchoolUser,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from SchoolUser a where a.alias = :alias")
    SchoolUser findUserForWrite(@Param("alias") String alias);

}
