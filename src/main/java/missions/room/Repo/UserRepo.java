package missions.room.Repo;

import CrudRepositories.UserCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Users.BaseUser;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CommonsLog
@Service
public class UserRepo {

    @Autowired
    private final UserCrudRepository userCrudRepository;

    public UserRepo(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    @Transactional
    public Response<BaseUser> findUserForWrite(String alias){
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
    public Response<BaseUser> findUserForRead(String alias){
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
            return new Response<>(alias!=null && userCrudRepository.existsById(alias), OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<BaseUser> save(BaseUser baseUser){
        try {
            return new Response<>(userCrudRepository.save(baseUser), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<BaseUser> findUser(String alias) {
        try {
            Optional<BaseUser> user=userCrudRepository.findById(alias);
            return user.map(value -> new Response<>(value, OpCode.Success)).
                    orElseGet(() -> new Response<>(null, OpCode.Not_Exist));
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<List<BaseUser>> findAllUsers() {
        try{
            List<BaseUser> userLIES = Lists.newArrayList(userCrudRepository.findAll());
            return new Response<>(userLIES,OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(new ArrayList<>(),OpCode.DB_Error);
        }

    }

    public Response<Boolean> delete(BaseUser user) {
        try{
            userCrudRepository.delete(user);
            return new Response<>(true, OpCode.Success);
        }
        catch (Exception e){
            log.error("error to delete user " + user, e);
            return new Response<>(false, OpCode.DB_Error);
        }
    }
}
