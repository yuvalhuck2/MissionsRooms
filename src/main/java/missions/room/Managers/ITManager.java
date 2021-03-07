package missions.room.Managers;

import DataAPI.OpCode;
import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import ExternalSystems.HashSystem;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.IT;
import missions.room.Repo.ITRepo;
import missions.room.Repo.UserRepo;
import Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class ITManager {

    protected static final Object ADD_USER_LOCK = new Object();

    @Autowired
    protected ITRepo itRepo;

    @Autowired
    protected UserRepo userRepo;

    private Ram ram;

    private HashSystem hashSystem;

    public ITManager() {
        this.ram = new Ram();
        hashSystem=new HashSystem();
    }

    protected Response<IT> checkIT(String apiKey){
        String alias = ram.getAlias(apiKey);
        if(alias==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        Response<IT> ITResponse = itRepo.findITById(alias);
        return checkITResponse(ITResponse);
    }

    private Response<IT> checkITResponse(Response<IT> ITResponse){
        if(ITResponse.getReason()!= OpCode.Success){
            log.error("connection to db lost");
            return new Response<>(null,ITResponse.getReason());
        }
        IT it = ITResponse.getValue();
        if(it == null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(it,OpCode.Success);
    }

    /**
     * req 6.4 - adding new IT to the system
     * @param apiKey - authentication object
     * @param newUser - new IT details
     * @return if user added successfully
     */
    public Response<Boolean> addNewIT(String apiKey, RegisterDetailsData newUser){
        String alias=newUser.getAlias();
        String password=newUser.getPassword();
        if(!Utils.checkString(alias)){
            log.info("add IT with empty or null alias");
            return new Response<>(false,OpCode.Wrong_Alias);
        }
        if(!Utils.checkString(password)){
            log.info("add IT with empty or null password");
            return new Response<>(false,OpCode.Wrong_Password);
        }
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }
        return saveITInDB(alias, password,itResponse.getValue().getAlias());
    }

    private Response<Boolean> saveITInDB( String alias, String password,String myAlias) {
        password= hashSystem.encrypt(password);
        synchronized (ADD_USER_LOCK){
            Response<Boolean> userResponse = userRepo.isExistsById(alias);
            if(userResponse.getReason()!= OpCode.Success){
                log.error("connection to db lost");
                return new Response<>(false,userResponse.getReason());
            }
            if(userResponse.getValue()){
                log.info("there is user with that name already");
               return new Response<>(false,OpCode.Already_Exist);
            }
            Response<IT> itResponse= itRepo.save(new IT(alias,password));
            if(itResponse.getReason()!=OpCode.Success){
                log.error("connection to db lost");
                return new Response<>(false,itResponse.getReason());
            }
            log.info(myAlias + " added " + alias + " to be new IT successfully");
            return new Response<>(true,OpCode.Success);
        }
    }
}
