package missions.room.Communications.Controllers;

import DataAPI.CloseRoomData;
import DataAPI.Response;
import DataAPI.NewRoomDetails;
import missions.room.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
