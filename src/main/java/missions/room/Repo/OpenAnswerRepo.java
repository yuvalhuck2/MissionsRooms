package missions.room.Repo;

import lombok.extern.apachecommons.CommonsLog;
import CrudRepositories.OpenAnswerRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.OpenAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import Utils.Utils;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@CommonsLog
public class OpenAnswerRepo {

    @Autowired
    private OpenAnswerRepository openAnswerRepository;

    private boolean saveOpenAnswerFile(String roomId, String missionId, MultipartFile file){
        String rootDirectory = Utils.getRootDirectory();
        Path folderPath = FileSystems.getDefault().getPath(rootDirectory,"openAnswer", roomId, missionId);
        Path filePath = Paths.get(folderPath.toString(), file.getOriginalFilename());
        File folder = folderPath.toFile();
        try {
            if (!folder.exists()){
                folder.mkdirs();
            }
            file.transferTo(filePath.toAbsolutePath());
        } catch (Exception e) {
            //log.error(String.format("Error while saving file. stack trace: {0}", e.getStackTrace()));
            return false;
        }
        return true;
    }
    public Response<Boolean> saveOpenAnswer(OpenAnswer openAnswer){
        Response res = new Response(true, OpCode.Success);
        if (openAnswer.isHasFile() && saveOpenAnswerFile(openAnswer.getRoomId(), openAnswer.getMissionId(), openAnswer.getFile())){
            try{
                openAnswerRepository.save(openAnswer);
            } catch(Exception e) {
                res.setValue(false);
                res.setReason(OpCode.DB_Error);
            }
        } else {
            res.setValue(false);
            res.setReason(OpCode.FAILED_TO_SAVE_FILE);
        }
        return res;
    }
}
