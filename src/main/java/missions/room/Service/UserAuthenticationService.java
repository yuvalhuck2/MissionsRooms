package missions.room.Service;

import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import missions.room.Managers.UserAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationManager userAuthenticationManager;

    /**
     * req 2.2 - register
     * @param details - user details
     * @return if mail with the code was sent successfully
     */
    public Response<Boolean> register (RegisterDetailsData details){
        return userAuthenticationManager.register(details);
    }


    /**
     * req 2.2 - register code
     * * @param alias - user alias
     * @param code - user details
     * @return if register succeeded
     */
    public Response<Boolean> registerCode (String alias, String code){
        return userAuthenticationManager.registerCode(alias,code);
    }

    /**
     * req 2.3 -login
     * @param alias - user alias
     * @param password - user password
     * @return API key if login succeeded
     */
    public Response<String> login (String alias, String password){
        return userAuthenticationManager.login(alias,password);
    }

    public void setExternalSystems(MailSender mailSender, VerificationCodeGenerator verificationCodeGenerator){
        if (mailSender != null){
            userAuthenticationManager.setSender(mailSender);
        }

        if(verificationCodeGenerator != null){
            userAuthenticationManager.setVerificationCodeGenerator(verificationCodeGenerator);
        }
    }


}
