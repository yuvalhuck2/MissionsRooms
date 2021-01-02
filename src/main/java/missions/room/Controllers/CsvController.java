package missions.room.Controllers;

import missions.room.Managers.UploadCsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CsvController {

//    @Autowired
//    private UploadCsvService uploadCsvService;

    @Autowired
    private UploadCsvManager uploadCsvManager;

    @PostMapping("/uploadCsv")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadFileResponse uploadFile(@RequestParam("files") MultipartFile[] files, @RequestParam String token) {

        //Reader reader = new InputStreamReader(file.getInputStream());
        //
        uploadCsvManager.uploadCsv(token, files);
        /*try {
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        UploadFileResponse res = new UploadFileResponse("csv", 100);
        return res;
    }
}
