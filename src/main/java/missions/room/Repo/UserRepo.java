package missions.room.Repo;

import CrudRepositories.UserCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;

@Service
public class UserRepo {

    @Autowired
    private final UserCrudRepository userCrudRepository;

    public UserRepo(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    @Transactional
    public Response<User> findUserForWrite(String alias){
        try {
            return new Response<>(userCrudRepository.findUserForWrite(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<User> findUserForRead(String alias){
        try {
            return new Response<>(userCrudRepository.findUserForRead(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Boolean> isExistsById(String alias){
        try {
            return new Response<>(userCrudRepository.existsById(alias), OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<User> save(User user){
        try {
            return new Response<>(userCrudRepository.save(user), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

}
