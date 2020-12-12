package CrudRepositories;

import Domain.Mission;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface MissionCrudRepository extends CrudRepository<Mission,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Mission a where a.missionId = :missionId")
    Mission findMissionForWrite(@Param("missionId") String missionId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Mission a where a.missionId = :missionId")
    Mission findMissionForRead(@Param("missionId") String missionId);
}
