package CrudRepositories;

import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.StudentRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRoomCrudRepository extends CrudRepository<StudentRoom,String> {

    @Query("select distinct sr FROM Student_Room sr " +
            "where gr.participant_alias= :alias")
    List<StudentRoom> findStudentRoomByStudent(String alias);
}
