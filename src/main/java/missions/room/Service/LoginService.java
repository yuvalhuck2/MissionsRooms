package missions.room.Service;

import DataAPI.OpCode;
import DataAPI.Response;
import Utils.Utils;
import missions.room.Managers.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class LoginService {

    @Autowired
    private LoginManager loginManager;

    /**
     * req 2.3 -login
     * @param alias - user alias
     * @param password - user password
     * @return API key if login succeeded
     */
    public Response<String> login (String alias, String password){
        return loginManager.login(alias,password);
    }
}
