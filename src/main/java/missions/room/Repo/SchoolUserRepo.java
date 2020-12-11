package missions.room.Repo;

import DataAPI.OpCode;
import DataAPI.Response;
import Domain.SchoolUser;
import CrudRepositories.SchoolUserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;

@Service
public class SchoolUserRepo {

    @Autowired
    private final SchoolUserCrudRepository schoolUserCrudRepository;

    public SchoolUserRepo(SchoolUserCrudRepository schoolUserCrudRepository) {
        this.schoolUserCrudRepository = schoolUserCrudRepository;
    }

    @Transactional
    public Response<SchoolUser> findUserForWrite(String alias){
        try {
            return new Response<>(schoolUserCrudRepository.findUserForWrite(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<SchoolUser> findUserForRead(String alias){
        try {
            return new Response<>(schoolUserCrudRepository.findUserForRead(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<SchoolUser> save(SchoolUser schoolUser){
        try {
            return new Response<>(schoolUserCrudRepository.save(schoolUser), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

}
