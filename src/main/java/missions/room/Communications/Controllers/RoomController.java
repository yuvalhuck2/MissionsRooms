package missions.room.Communications.Controllers;

import DataAPI.*;
import missions.room.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/room") // This means URL's start with /demo (after Application path)
public class RoomController extends AbsController{

    @Autowired
    private RoomService roomService;

    public RoomController() {
        super();
    }

    @PostMapping("/create")
    public Response<?> createRoom(@RequestBody String createRoomData) {
        NewRoomDetails data = json.fromJson(createRoomData, NewRoomDetails.class);
        Response<Boolean> response = roomService.createRoom(data.getApiKey(),data);
        return response;
    }

    @PostMapping("/close")
    public Response<?> closeRoom(@RequestBody String closeRoomData) {
        CloseRoomData data = json.fromJson(closeRoomData, CloseRoomData.class);
        Response<Boolean> response = roomService.closeRoom(data.getApiKey(),data.getRoomId());
        return response;
    }

    @PostMapping("/classroom")
    public Response<?> getClassRoomData(@RequestBody String apiKey) {
        ApiKey data = json.fromJson(apiKey, ApiKey.class);
        Response<ClassRoomData> response = roomService.getClassRoomData(data.getApiKey());
        return response;
    }

    @PostMapping("/responseAnswer")
    public Response<?> responseStudentSolution(@RequestBody String resolveMissionData) {
        ResolveMissionData missionResolutionData = json.fromJson(resolveMissionData, ResolveMissionData.class);
        return  roomService.responseStudentSolution(missionResolutionData.getApiKey(), missionResolutionData.getRoomId(), missionResolutionData.getMissionId(), missionResolutionData.isApprove());
    }
    @PostMapping("/view")
    public Response<?> watchRoomDetails(@RequestBody String apiKeyObject) {
        ApiKey data = json.fromJson(apiKeyObject, ApiKey.class);
        Response<List<RoomsDataByRoomType>> response = roomService.watchMyClassroomRooms(data.getApiKey());
        return response;
    }

}
