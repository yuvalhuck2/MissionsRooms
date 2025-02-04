package missions.room.RoomManagerMockitoTests;


import CrudRepositories.RoomCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.StudentCrudRepository;
import Data.Data;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.RoomManager;
import missions.room.Repo.RoomRepo;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

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
        roomCrudRepository.save(room);

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
        roomTemplateCrudRepository.deleteAll();
        super.tearDown();
    }
}
