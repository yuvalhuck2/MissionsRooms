package missions.room.Repo;

import CrudRepositories.GroupRoomCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupRoomRepo {

    @Autowired
    private GroupRoomCrudRepository groupRoomCrudRepository;

    public Response<List<GroupRoom>> findGroupRoomByGroup(String group) {

        try {
            List<GroupRoom> groupRooms=groupRoomCrudRepository.findGroupRoomByGroup(group);
            if(groupRooms==null){
                return new Response<>(null, OpCode.Not_Exist);
            }
            return new Response<>(groupRooms, OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
