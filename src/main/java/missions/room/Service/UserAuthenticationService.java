package missions.room.Service;

import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import DataAPI.TeacherData;
import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import DataAPI.GroupType;
import missions.room.Managers.UserAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationManager userAuthenticationManager;

    /**
     * req 2.2 - register
     * @param details - user details
     * @return if mail with the code was sent successfully
     */
    public Response<List<TeacherData>> register (RegisterDetailsData details){
        return userAuthenticationManager.register(details);
    }


    /**
     * req 2.2 - register code
     * * @param alias - user alias
     * @param code - user details
     * @return if register succeeded
     */
    public Response<Boolean> registerCode (String alias, String code, String teacherAlias, GroupType groupType){
        return userAuthenticationManager.registerCode(alias,code,teacherAlias,groupType);
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

    /**
     * req 3.3
     * @param apiKey - authentication object
     * @param newPassword - the new password to change to
     * @return if the password was changed
     */
    public Response<Boolean> changePassword (String apiKey, String newPassword){
        return userAuthenticationManager.changePassword(apiKey,newPassword);
    }

    public void setExternalSystems(MailSender mailSender, VerificationCodeGenerator verificationCodeGenerator){
        if (mailSender != null){
            userAuthenticationManager.setSender(mailSender);
        }

        if(verificationCodeGenerator != null){
            userAuthenticationManager.setVerificationCodeGenerator(verificationCodeGenerator);
        }
    }


    public void openWebSocket(String apiKey) {
        userAuthenticationManager.openWebSocket(apiKey);
    }

    public void closeWebsocket(String userId) {
        userAuthenticationManager.closeWebSocket(userId);
    }
}
