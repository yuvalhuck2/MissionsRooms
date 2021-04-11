package missions.room.Communications.Controllers;

import DataAPI.*;
import missions.room.Service.ITService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This means that this class is a Controller
@RequestMapping(path="/it")
public class ITController extends AbsController{

    @Autowired
    private ITService itService;

    @PostMapping("/add/it")
    public Response<?> addNewIT(@RequestBody String itDetailsDataStr){
        AddITData itDetailsData= json.fromJson(itDetailsDataStr, AddITData.class);
        return itService.addNewIT(itDetailsData);
    }

    @PostMapping("/view/all")
    public Response<?> getAllUsersSchoolProfiles(){
        return itService.getAllUsersSchoolProfiles();
    }

    @PostMapping("/update")
    public Response<?> UpdateUserDetails(@RequestBody String updateUserStr){
        UserProfileData updateUser= json.fromJson(updateUserStr, UserProfileData.class);
        return itService.updateUserDetails(updateUser.getApiKey(),updateUser);
    }

    @PostMapping("/add/student")
    public Response<?> addNewStudent(@RequestBody String studentDetailsStr){
        StudentData studentData= json.fromJson(studentDetailsStr, StudentData.class);
        return itService.addStudent(studentData.getApKey(), studentData);
    }

    @PostMapping("/add/teacher")
    public Response<?> addNewTeacher(@RequestBody String teacherDetailsData){
        TeacherData teacherData= json.fromJson(teacherDetailsData, TeacherData.class);
        return itService.addTeacher(teacherData.getApiKey(), teacherData);
    }
}
