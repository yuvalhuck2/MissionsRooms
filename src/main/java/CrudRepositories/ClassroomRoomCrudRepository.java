package CrudRepositories;

import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClassroomRoomCrudRepository extends CrudRepository<ClassroomRoom,String> {

    @Query("select distinct crr FROM Classroom_Room crr " +
            "where crr.paticipant_class_name= :classroom")
    List<ClassroomRoom> findClassroomRoomByClassroom(String classroom);
}
