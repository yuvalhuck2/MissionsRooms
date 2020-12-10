package Service;

import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import missions.room.Managers.RegisterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private RegisterManager registerManager;

    /**
     * req 2.2 - register
     * @param details - user details
     * @return if mail with the code was sent successfully
     */
    public Response<Boolean> register (RegisterDetailsData details){
        return registerManager.register(details);
    }


    /**
     * req 2.2 - register code
     * * @param alias - user alias
     * @param code - user details
     * @return if register succeeded
     */
    public Response<Boolean> registerCode (String alias, String code){
        return registerManager.registerCode(alias,code);
    }
}
