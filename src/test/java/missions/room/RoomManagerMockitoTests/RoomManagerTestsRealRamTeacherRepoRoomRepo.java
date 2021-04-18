package missions.room.RoomManagerMockitoTests;


import CrudRepositories.RoomCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.StudentCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.RoomManager;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.RoomRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static Data.DataConstants.WRONG_ROOM_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomManagerTestsRealRamTeacherRepoRoomRepo extends RoomManagerTestsRealRamTeacherRepo {

    @Autowired
    private RoomRepo roomRepoReal;

    @Autowired
    private RoomCrudRepository roomCrudRepository;

    @Autowired
    private StudentCrudRepository studentCrudRepository;

    @Autowired
    private RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Override
    protected void initRoomRepo(Teacher teacher, Student student) {
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        //roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID_2Mission_Class));
        //studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        //studentCrudRepository.save(dataGenerator.getStudent(Data.VALID2));
        roomCrudRepository.save(room);
        //roomCrudRepository.save(dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups));

        try {
            Field roomRepo = RoomManager.class.getDeclaredField("roomRepo");
            roomRepo.setAccessible(true);
            roomRepo.set(roomManager,roomRepoReal);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }

    }



    @Override
    @AfterEach
    void tearDown() {

        roomCrudRepository.deleteAll();
        //studentCrudRepository.deleteAll();
        roomTemplateCrudRepository.deleteAll();

        super.tearDown();
    }
}
