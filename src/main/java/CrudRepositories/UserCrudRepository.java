package CrudRepositories;

import missions.room.Domain.Users.BaseUser;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface UserCrudRepository extends CrudRepository<BaseUser,String> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from BaseUser a where a.alias = :alias")
    BaseUser findUserForWrite(@Param("alias") String alias);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from BaseUser a where a.alias = :alias")
    BaseUser findUserForRead(@Param("alias") String alias);
}
