package missions.room.Communications.Controllers;

import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Managers.UploadCsvManager;
import missions.room.Service.UploadCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CsvController {

    @Autowired
    private UploadCsvService uploadCsvService;

//    @Autowired
//    private UploadCsvManager uploadCsvManager;

    @PostMapping("/uploadCsv")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Response<Boolean> uploadFile(@RequestParam("files") MultipartFile[] files, @RequestParam String token) {
        return uploadCsvService.uploadCsv(token, files);
    }
}
