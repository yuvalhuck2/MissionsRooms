package CrudRepositories;

import missions.room.Domain.Room;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from StudentRoom a where a.participant = :alias")
    StudentRoom findStudentRoomForWriteByAlias(@Param("alias") String alias);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from GroupRoom a where a.participant = :groupName")
    GroupRoom findGroupRoomForWriteByAlias(@Param("groupName") String groupName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from ClassroomRoom a where a.participant = :classroomName")
    ClassroomRoom findClassroomRoomForWriteByAlias(@Param("classroomName") String classroomName);
}