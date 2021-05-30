package missions.room.Repo;

import CrudRepositories.MissionCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.missions.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MissionRepo {

    @Autowired
    private MissionCrudRepository missionCrudRepository;

    public MissionRepo(MissionCrudRepository missionCrudRepository) {
        this.missionCrudRepository = missionCrudRepository;
    }

    public Response<Mission> findMissionById(String alias){
        try{
            Optional<Mission> missionOptional= missionCrudRepository.findById(alias);
            Mission mission=null;
            if(missionOptional.isPresent()){
                mission=missionOptional.get();
            }
            return new Response<>(mission, OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<List<Mission>> findAllMissions(){
        try{
            Iterable<Mission> iter=missionCrudRepository.findAll();
            List<Mission> missions=new ArrayList<>();
            for (Mission item : iter) {
                missions.add(item);
            }
            return new Response<>(missions,OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Mission> save(Mission mission){
        try {
            return new Response<>(missionCrudRepository.save(mission), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
