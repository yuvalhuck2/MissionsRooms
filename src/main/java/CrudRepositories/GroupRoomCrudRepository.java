package CrudRepositories;

import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRoomCrudRepository extends CrudRepository<GroupRoom,String> {

    @Query("select distinct gr FROM Group_Room gr " +
            "where gr.paticipant_group_name= :groupName")
    List<GroupRoom> findGroupRoomByGroup(String groupName);
}
