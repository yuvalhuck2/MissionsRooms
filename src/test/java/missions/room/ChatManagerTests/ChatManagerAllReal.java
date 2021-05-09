package missions.room.ChatManagerTests;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.RoomCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.StudentCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.ChatManager;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.RoomManager;
import missions.room.Repo.RoomRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ChatManagerAllReal extends ChatManagerTestsRealRamSchoolUserRepo {

    @Mock
    private RoomCrudRepository mockRoomCrudRepository;

    @Autowired
    private RoomRepo roomRepoReal;

    @Autowired
    private RoomCrudRepository roomCrudRepository;

    @Autowired
    private RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Autowired
    private MissionCrudRepository missionCrudRepository;

    @Override
    protected void initRoomRepo() {
        missionCrudRepository.save(room.getRoomTemplate().getMission(0));
        roomTemplateCrudRepository.save(room.getRoomTemplate());
        roomCrudRepository.save(room);

        try {
            Field roomRepo = ChatManager.class.getDeclaredField("roomRepo");
            roomRepo.setAccessible(true);
            roomRepo.set(chatManager,roomRepoReal);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }

    }

    @Test
    void sendMessageRoomRepoFindRoomForReadThrowsException() {
        when(mockRoomCrudRepository.findRoomForRead(any()))
                .thenThrow(new DataRetrievalFailureException(""));
        try {
            Field classroomRepo = ChatManager.class.getDeclaredField("roomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(chatManager,new RoomRepo(mockRoomCrudRepository));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        super.sendMessageRoomRepoFindRoomForReadThrowsException();
    }

    @Test
    void sendMessageHappyCase(){
        Response<String> chatResponse = chatManager.sendMessage(teacherApiKey, MESSAGE ,roomId);
        assertEquals(chatResponse.getReason(), OpCode.Success);
        assertEquals(chatResponse.getValue(), "");
    }

    @Override
    protected void tearDownMocks() {
        roomCrudRepository.deleteAll();
        roomTemplateCrudRepository.deleteAll();
        missionCrudRepository.deleteAll();
        super.tearDownMocks();

    }
}
