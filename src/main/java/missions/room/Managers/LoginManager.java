package missions.room.Managers;

import DataAPI.OpCode;
import DataAPI.Response;
import Domain.Ram;
import Domain.Rooms.User;
import Domain.SchoolUser;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import missions.room.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {


    @Autowired
    private UserRepo userRepo;

    //save verification codes and string for trace and clean old code
    private final Ram ram;

    public LoginManager(){
        this.ram=new Ram();
    }

    /**
     * req 2.3 -login
     * @param alias - user alias
     * @param password - user password
     * @return API key if login succeeded
     */
    public Response<String> login (String alias, String password){
        if(!Utils.checkString(password)){
            return new Response<>(null,OpCode.Wrong_Password);
        }
        if(!Utils.checkString(alias)){
            return new Response<>(null,OpCode.Wrong_Alias);
        }
        Response<User> rsp= userRepo.findUserForRead(alias);
        if (rsp.getReason()!=OpCode.Success){
            return new Response<>(null, rsp.getReason());
        }
        User user =rsp.getValue();
        if (user ==null){
            return new Response<>(null, OpCode.Not_Exist);
        }
        String api=UniqueStringGenerator.getUniqueCode(alias);
        if(user.getPassword().equals(password)){
            this.ram.addApi(api,alias);
            return new Response<>(api,OpCode.Success);
        }
        else{
            return new Response<>(null,OpCode.Wrong_Password);
        }
    }
}
