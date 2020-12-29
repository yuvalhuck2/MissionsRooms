package missions.room.Communications.Controllers;

import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import DataAPI.TeacherData;
import com.google.gson.Gson;
import missions.room.Service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/UserAuth") // This means URL's start with /demo (after Application path)
public class UserAuthenticationController {

    @Autowired
    private UserAuthenticationService authenticationService;

    private Gson json ;

    public UserAuthenticationController() {
        json=new Gson();
    }

    @PostMapping()
    public ResponseEntity<?> addStore(@RequestBody String registerDetailsDataStr){
        RegisterDetailsData registerDetailsData= json.fromJson(registerDetailsDataStr,RegisterDetailsData.class);
        Response<List<TeacherData>> listResponse=authenticationService.register(registerDetailsData);
        return getResponseEntity(listResponse);
    }

    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
