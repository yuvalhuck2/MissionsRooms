package missions.room.Service;

import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Managers.UploadCsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadCsvService {

    @Autowired
    private UploadCsvManager uploadCsvManager;

    public UploadCsvService() {
    }

    public Response<Boolean> uploadCsv(String token, MultipartFile[] CSVs){
        try {
            return uploadCsvManager.uploadCsv(token, CSVs);
        } catch (Exception e) {
            return new Response<>(false, OpCode.Null_Error);
        }
    }
}
