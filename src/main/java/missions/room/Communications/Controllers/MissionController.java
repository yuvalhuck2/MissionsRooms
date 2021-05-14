package missions.room.Communications.Controllers;

import DataObjects.APIObjects.ApiKey;
import DataObjects.APIObjects.CreateMissionData;
import DataObjects.APIObjects.RoomIdAndApiKeyData;
import DataObjects.APIObjects.RoomOpenAnswerData;
import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import Utils.Utils;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/mission") // This means URL's start with /demo (after Application path)
@CommonsLog
public class MissionController extends AbsController {

    @Autowired
    private MissionService missionService;

    public MissionController() {
        super();
    }

    @PostMapping("/create")
    public Response<?> creatMission(@RequestBody String createMissionData) {
        CreateMissionData data = json.fromJson(createMissionData, CreateMissionData.class);
        Response<Boolean> response = missionService.createMission(data.getApiKey(),data.getMissionData());
        return response;
    }

    @PostMapping("/search")
    public Response<?> searchMissions(@RequestBody String apiKey) {
        ApiKey data = json.fromJson(apiKey, ApiKey.class);
        Response<List<MissionData>> response = missionService.searchMissions(data.getApiKey());
        return response;
    }

    @PostMapping("/unApprovedAnswers")
    public Response<?> getUnApproveAnswers(@RequestBody String apiKeyAndRoomId) {
        RoomIdAndApiKeyData data=json.fromJson(apiKeyAndRoomId, RoomIdAndApiKeyData.class);
        Response<RoomOpenAnswerData> res = missionService.watchSolutions(data.getApiKey(), data.getRoomId());
        return res;
    }

    @GetMapping("/downloadFile")
    public  ResponseEntity<?> downloadOpenAnswerFile(@RequestParam String apiKey, @RequestParam(name = "roomId") String roomId, @RequestParam(name = "missionId") String missionId) {
        Response<File> fileRes = missionService.getMissionOpenAnswerFile(apiKey, roomId, missionId);
//        String rootPath = Utils.getRootDirectory();
//        Path folderPath = FileSystems.getDefault().getPath(rootPath,"openAnswer", roomId, missionId);
        if (fileRes.getReason() != OpCode.Success) {
            //TODO add logs
            return null;
        }
        File f = fileRes.getValue(); //TODO add defense
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(f));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", f.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(f.length())
                    .contentType(MediaType.parseMediaType("application/txt")).body(resource);
            log.info(responseEntity);
            return  responseEntity;
        } catch (FileNotFoundException e) { //TODO add log
            log.error("error down;pad file", e);
        }

        return null;

    }
}
