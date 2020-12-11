package CrudRepositories;

import Domain.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface UserCrudRepository extends CrudRepository<User,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from User a where a.alias = :alias")
    User findUserForWrite(@Param("alias") String alias);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from User a where a.alias = :alias")
    User findUserForRead(@Param("alias") String alias);
}
