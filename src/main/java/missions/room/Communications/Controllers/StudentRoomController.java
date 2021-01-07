package missions.room.Communications.Controllers;

import DataAPI.*;
import missions.room.Service.StudentRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/student/room") // This means URL's start with /demo (after Application path)
public class StudentRoomController extends AbsController{

    @Autowired
    private StudentRoomService studentRoomService;

    public StudentRoomController() {
        super();
    }

    @PostMapping("/view")
    public Response<?> getClassRoomData(@RequestBody String apiKey) {
        ApiKey data = json.fromJson(apiKey, ApiKey.class);
        Response<List<RoomDetailsData>> response = studentRoomService.watchRoomDetails(data.getApiKey());
        return response;
    }

    @PostMapping("/deterministic")
    public Response<?> answerDeterministicQuestion(@RequestBody String answerData) {
        AnswerDeterministicData data= json.fromJson(answerData, AnswerDeterministicData.class);
        Response<Boolean> response = studentRoomService.answerDeterministicQuestion(data.getApiKey(),
                data.getRoomId(),data.isAnswer());
        return response;
    }
}
