package missions.room.Communications.Controllers;

import DataAPI.*;
import missions.room.Service.ITService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/classroom/close")
    public Response<?> closeClassroom(@RequestBody String classRoomDataString){
        ClassRoomData classRoomData = json.fromJson(classRoomDataString, ClassRoomData.class);
        return itService.closeClassroom(classRoomData.getApiKey(), classRoomData.getName());
    }

    @PostMapping("/delete/user")
    public Response<?> deleteUser(@RequestBody String apiKeyAndAlias){
        ApiKeyAndAliasData data=json.fromJson(apiKeyAndAlias,ApiKeyAndAliasData.class);
        return itService.deleteUser(data.getApiKey(),data.getAlias());
    }

    @PostMapping("/delete/senior")
    public Response<?> deleteSeniorStudents(@RequestBody String apiKey){
        ApiKey data=json.fromJson(apiKey,ApiKey.class);
        return itService.deleteSeniorStudents(data.getApiKey());
    }

    @PostMapping("/transfer/teacher")
    public Response<?> transferTeacherClassroom(@RequestBody String transferDetails){
        TransferDetailsData data= json.fromJson(transferDetails, TransferDetailsData.class);
        return itService.transferTeacherClassroom(data.getApiKey(),data.getAlias(),data.getClassroomName(),data.getGroupType());
    }

    @PostMapping("/get/classrooms")
    public Response<?> getAllClassrooms(@RequestBody String apikey){
        ApiKey data=json.fromJson(apikey,ApiKey.class);
        return itService.getAllClassrooms(data.getApiKey());
    }

    @PostMapping("/get/classrooms/grade")
    public Response<?> getAllClassroomsByGrade(@RequestBody String apikeyAndAlias){
        ApiKeyAndAliasData data=json.fromJson(apikeyAndAlias,ApiKeyAndAliasData.class);
        return itService.getAllClassroomsByGrade(data.getApiKey(),data.getAlias());
    }

    @PostMapping("/transfer/student")
    public Response<?> transferStudentClassroom(@RequestBody String transferDetails){
        TransferDetailsData data=json.fromJson(transferDetails,TransferDetailsData.class);
        return itService.transferStudentClassroom(data.getApiKey(),data.getAlias(),data.getClassroomName(),data.getGroupType());
    }

}
