package missions.room.Managers;

import DataAPI.Response;
import DataAPI.RoomTemplateDetailsData;
import missions.room.Repo.RoomTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddRoomTemplateManager {

    @Autowired
    private RoomTemplateRepo roomTemplateRepo;


    public Response<Boolean> createRoomTemplate(String apiKey, RoomTemplateDetailsData details) {
        throw new ExceptionInInitializerError();
    }
}
