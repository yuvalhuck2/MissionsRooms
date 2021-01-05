package missions.room.Controllers;

import DataAPI.OpCode;
import DataAPI.Response;
import Service.UploadCsvService;
import missions.room.Managers.UploadCsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;

@RestController
public class CsvController {

//    @Autowired
//    private UploadCsvService uploadCsvService;

    @Autowired
    private UploadCsvManager uploadCsvManager;

    @PostMapping("/uploadCsv")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Response<Boolean> uploadFile(@RequestParam("files") MultipartFile[] files, @RequestParam String token) {


        try {
            return uploadCsvManager.uploadCsv(token, files);
        } catch (Exception e) {
            return new Response<>(false, OpCode.Null_Error);
        }

    }
}
