package missions.room.Repo;

import CrudRepositories.SchoolUserCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.Users.BaseUser;
import missions.room.Domain.Users.SchoolUser;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public Response<SchoolUser> findUserForRead(String alias){
        try {
            Optional<SchoolUser> schoolUser=schoolUserCrudRepository.findById(alias);
            return schoolUser.map(user -> new Response<>(user, OpCode.Success)).orElseGet(() -> new Response<>(null, OpCode.Success));
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

    public Response<List<BaseUser>> findAllSchoolUsers() {
        try{
            List<BaseUser> userLIES = Lists.newArrayList(schoolUserCrudRepository.findAll());
            return new Response<>(userLIES,OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(new ArrayList<>(),OpCode.DB_Error);
        }
    }
}
