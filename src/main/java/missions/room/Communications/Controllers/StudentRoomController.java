package missions.room.Communications.Controllers;

import DataObjects.APIObjects.*;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.RoomDetailsData;
import missions.room.Service.StudentRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/trivia")
    public Response<?> answerTriviaMission(@RequestBody String answerData){
        AnswerTriviaData data = json.fromJson(answerData, AnswerTriviaData.class);
        return studentRoomService.answerTriviaMission(
                data.getApiKey(),
                data.getRoomId(),
                data.isAnswer()
        );
    }

    @PostMapping("/openAns")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> answerOpenQuestion(@RequestParam(name = "file", required = false) MultipartFile file, @RequestParam("missionId") String missionId, @RequestParam("roomId") String roomId, @RequestParam(name = "openAnswer", defaultValue = "") String openAnswer, @RequestParam String token) {
        SolutionData solutionData = new SolutionData(missionId, roomId, openAnswer);
        return studentRoomService.answerOpenQuestionMission(token, solutionData, file);
    }

    @PostMapping("/story")
    public Response<?> answerStory(@RequestBody String storyAnswer) {
        StoryAnswerData data= json.fromJson(storyAnswer, StoryAnswerData.class);
        return studentRoomService.answerStoryMission(data.getApiKey(), data.getRoomId(), data.getSentence());
    }

    @PostMapping("/finish/story")
    public Response<?> finishStory(@RequestBody String storyFinish) {
        RoomIdAndApiKeyData data= json.fromJson(storyFinish, RoomIdAndApiKeyData.class);
        return studentRoomService.finishStoryMission(data.getApiKey(), data.getRoomId());
    }
}
