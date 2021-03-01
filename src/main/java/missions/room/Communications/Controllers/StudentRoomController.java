package missions.room.Communications.Controllers;

import DataAPI.*;
import missions.room.Communications.Publisher.SinglePublisher;
import missions.room.Domain.Notifications.NonPersistenceNotification;
import missions.room.Service.StudentRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
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
    public Response<?> watchRoomDetails(@RequestBody String apiKeyObject) {
        ApiKey data = json.fromJson(apiKeyObject, ApiKey.class);
        Response<List<RoomDetailsData>> response = studentRoomService.watchRoomDetails(data.getApiKey());
        return response;
    }

    @PostMapping("/data")
    public Response<?> watchRoomData(@RequestBody String apiKeyAndRoomIdData) {
        RoomIdAndApiKeyData data= json.fromJson(apiKeyAndRoomIdData, RoomIdAndApiKeyData.class);
        Response<RoomDetailsData> response = studentRoomService.watchRoomData(data.getApiKey(),
                data.getRoomId());
        return response;
    }

    @PostMapping("/disconnect")
    public void disconnectFromRoom(@RequestBody String apiKeyAndRoomIdData) {
        RoomIdAndApiKeyData data= json.fromJson(apiKeyAndRoomIdData, RoomIdAndApiKeyData.class);
        studentRoomService.disconnectFromRoom(data.getApiKey(),
                data.getRoomId());
    }

    @PostMapping("/deterministic")
    public Response<?> answerDeterministicQuestion(@RequestBody String answerData) {
        AnswerDeterministicData data= json.fromJson(answerData, AnswerDeterministicData.class);
        Response<Boolean> response = studentRoomService.answerDeterministicQuestion(data.getApiKey(),
                data.getRoomId(),data.isAnswer());
        return response;
    }
    // @RequestParam("solutionData") SolutionData openAnswer,
    @PostMapping("/openAns")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> answerOpenQuestion(@RequestParam("file") MultipartFile file, @RequestParam String token) {
        return studentRoomService.answerOpenQuestionMission(token, null, file);
    }
}
