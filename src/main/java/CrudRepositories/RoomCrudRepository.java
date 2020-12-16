package CrudRepositories;

import missions.room.Domain.Room;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface RoomCrudRepository extends CrudRepository<Room,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Room a where a.roomId = :roomId")
    Room findRoomForWrite(@Param("roomId") String roomId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select a from Room a where a.roomId = :roomId")
    Room findRoomForRead(@Param("roomId") String roomId);
}
