package missions.room.Repo;

import CrudRepositories.RoomCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import Utils.Utils;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.RoomOpenAnswersView;
import missions.room.Domain.Rooms.Room;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@CommonsLog
public class OpenAnswerRepo {

    @Autowired
    private RoomCrudRepository roomCrudRepository;

    private boolean saveOpenAnswerFile(String roomId, String missionId, MultipartFile file){
        String rootDirectory = Utils.getRootDirectory();
        Path folderPath = FileSystems.getDefault().getPath(rootDirectory,"openAnswer", roomId, missionId);
        Path filePath = Paths.get(folderPath.toString(), file.getOriginalFilename());
        File folder = folderPath.toFile();
        try {
            if (!folder.exists()){
                folder.mkdirs();
            }
            else if(folder.listFiles().length > 0) {
                FileUtils.cleanDirectory(folder);
            }
            file.transferTo(filePath.toAbsolutePath());
        } catch (Exception e) {
            log.error(String.format("Error while saving file. stack trace: %s, %s",
                    rootDirectory, filePath.toAbsolutePath()), e);
            return false;
        }
        return true;
    }
    public Response<Boolean> saveOpenAnswer(OpenAnswer openAnswer, Room room){
        Response res = new Response(true, OpCode.Success);
        if (!openAnswer.isHasFile() || saveOpenAnswerFile(room.getRoomId(), openAnswer.getMissionId(), openAnswer.getFile())){
            try{
                room.addOpenAnswer(openAnswer);
                roomCrudRepository.save(room);
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

    public Response<RoomOpenAnswersView> getOpenAnswers(String teacherAlias, String roomId) {
        if(!roomCrudRepository.existsById(roomId)) {
            return new Response<>(null, OpCode.INVALID_ROOM_ID);
        }
        try {
            RoomOpenAnswersView openAnswers = roomCrudRepository.findAByTeacherAndId(teacherAlias, roomId);
            return new Response<>(openAnswers, OpCode.Success);
        } catch (Exception e) {
            return new Response<>(null, OpCode.DB_Error);
        }
    }

}
