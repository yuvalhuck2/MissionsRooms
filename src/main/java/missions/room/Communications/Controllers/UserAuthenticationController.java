package missions.room.Communications.Controllers;

import DataObjects.APIObjects.RegisterCodeDetailsData;
import DataObjects.APIObjects.RegisterDetailsData;
import DataObjects.APIObjects.TeacherData;
import DataObjects.FlatDataObjects.Response;
import missions.room.Service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/UserAuth") // This means URL's start with /demo (after Application path)
public class UserAuthenticationController extends AbsController{

    @Autowired
    private UserAuthenticationService authenticationService;

    public UserAuthenticationController() {
        super();
    }

    @PostMapping()
    public Response<?> register(@RequestBody String registerCodeDetailsDataStr){
        RegisterDetailsData registerDetailsData= json.fromJson(registerCodeDetailsDataStr,RegisterDetailsData.class);
        Response<List<TeacherData>> listResponse=authenticationService.register(registerDetailsData);
        return listResponse;
    }

    @PostMapping("/code")
    public Response<?> registerCode(@RequestBody String registerDetailsDataStr){
        RegisterCodeDetailsData registerDetailsData= json.fromJson(registerDetailsDataStr, RegisterCodeDetailsData.class);
        Response<Boolean> listResponse=authenticationService.registerCode(registerDetailsData.getAlias(),
                registerDetailsData.getCode(),registerDetailsData.getTeacherAlias(),registerDetailsData.getGroupType());
        return listResponse;
    }

    @PostMapping("/login")
    public Response<?> login(@RequestBody String loginDetailsDataStr){
        RegisterDetailsData registerDetailsData= json.fromJson(loginDetailsDataStr,RegisterDetailsData.class);
        Response<String> loginResponse=authenticationService.login(registerDetailsData.getAlias(),registerDetailsData.getPassword());
        return loginResponse;
    }

    @PostMapping("/password/change")
    public Response<?> changePassword(@RequestBody String changePasswordDataStr){
        RegisterDetailsData changePasswordData= json.fromJson(changePasswordDataStr,RegisterDetailsData.class);
        Response<Boolean> changePassword=authenticationService.changePassword(changePasswordData.getApiKey(),changePasswordData.getPassword());
        return changePassword;
    }

    @PostMapping("/password/reset")
    public Response<?> resetPassword(@RequestBody String aliasStr){
        RegisterDetailsData resetPasswordData= json.fromJson(aliasStr,RegisterDetailsData.class);
        Response<Boolean> resetPassword=authenticationService.resetPassword(resetPasswordData.getAlias());
        return resetPassword;
    }

}
