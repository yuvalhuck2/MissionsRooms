package missions.room.Repo;

import CrudRepositories.MissionCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.util.Optional;

@Service
public class MissionRepo {

    @Autowired
    private MissionCrudRepository missionCrudRepository;

    public MissionRepo(MissionCrudRepository missionCrudRepository) {
        this.missionCrudRepository = missionCrudRepository;
    }

    @Transactional
    public Response<Mission> findMissionForWrite(String alias){
        try {
            return new Response<>(missionCrudRepository.findMissionForWrite(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<Mission> findMissionForRead(String alias){
        try{
            return new Response<>(missionCrudRepository.findMissionForRead(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
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

    public Response<Mission> save(Mission mission){
        try {
            return new Response<>(missionCrudRepository.save(mission), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
