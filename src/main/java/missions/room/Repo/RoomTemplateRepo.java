package missions.room.Repo;

import CrudRepositories.RoomTemplateCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.RoomTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomTemplateRepo {

    @Autowired
    private RoomTemplateCrudRepository roomTemplateCrudRepository;

    public RoomTemplateRepo(RoomTemplateCrudRepository roomTemplateCrudRepository) {
        this.roomTemplateCrudRepository=roomTemplateCrudRepository;
    }

    public Response<RoomTemplate> findRoomTemplateById(String roomTemplateId){
        try{
            Optional<RoomTemplate> templateOptional= roomTemplateCrudRepository.findById(roomTemplateId);
            RoomTemplate roomTemplate=null;
            if(templateOptional.isPresent()){
                roomTemplate=templateOptional.get();
            }
            return new Response<>(roomTemplate, OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<RoomTemplate> save(RoomTemplate roomTemplate){
        try {
            return new Response<>(roomTemplateCrudRepository.save(roomTemplate), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
