package missions.room.Repo;

import CrudRepositories.ITCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.Users.IT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ITRepo {

    @Autowired
    private final ITCrudRepository itCrudRepository;

    public ITRepo(ITCrudRepository itCrudRepository) {
        this.itCrudRepository = itCrudRepository;
    }

    public Response<IT> findITById(String alias){
        try{
            Optional<IT> studentOptional = itCrudRepository.findById(alias);
            IT it = null;
            if(studentOptional.isPresent()){
                it = studentOptional.get();
            }
            return new Response<>(it, OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<IT> save(IT it) {
        try {
            return new Response<>(itCrudRepository.save(it), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
