package Service;

import missions.room.Managers.UploadCsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UploadCsvService {

    @Autowired
    private UploadCsvManager uploadCsvManager;

    public UploadCsvService() {
    }

    public Boolean uploadCsv(String token, byte[] csv){
        //Reader targetReader = new InputStreamReader(new ByteArrayInputStream(csv));
        return true;
    }
}
