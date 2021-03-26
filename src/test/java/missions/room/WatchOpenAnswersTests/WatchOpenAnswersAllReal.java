package missions.room.WatchOpenAnswersTests;

import CrudRepositories.*;
import Data.Data;
import DataAPI.Response;
import missions.room.Domain.Classroom;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Users.Student;
import missions.room.Domain.missions.Mission;
import missions.room.Managers.MissionManager;
import missions.room.Managers.TriviaManager;
import missions.room.Repo.*;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

public class WatchOpenAnswersAllReal extends WatchOpenAnswersRealRamTeacherRepo{

    @Autowired
    private OpenAnswerRepo realOpenAnswerRepo;

    @Autowired
    private OpenAnswerRepository openAnswerRepository;

    @Autowired
    private RoomCrudRepository roomCrudRepository;

    @Autowired
    private RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Autowired
    private MissionCrudRepository missionCrudRepository;

    @Autowired
    private StudentCrudRepository studentCrudRepository;

    @Autowired
    private RoomRepo realRoomRepo;

    @Autowired
    private MissionRepo realMissionRepo;

    @Autowired
    private RoomTemplateRepo realRoomTemplateRepo;

    @Autowired
    private StudentRepo realStudentRepo;

    @Override
    protected void initMocks() {
        super.initMocks();
        try {
            Field openAnswerRepo = MissionManager.class.getDeclaredField("openAnswerRepo");
            openAnswerRepo.setAccessible(true);
            openAnswerRepo.set(missionManagerWithMockito, realOpenAnswerRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }

        Response<Student> res = realStudentRepo.save(dataGenerator.getStudent(Data.VALID));
        Response<Mission> res1 = realMissionRepo.save(dataGenerator.getMission(Data.VALID_OPEN_ANS));
        Response<RoomTemplate> res2 = realRoomTemplateRepo.save(dataGenerator.getRoomTemplate(Data.VALID_OPEN_ANS));
        Response<Room> res3 =realRoomRepo.save(dataGenerator.getRoom(Data.VALID_OPEN_ANS));
        System.out.println("S");

    }

    @AfterEach
    void tearDown() {
        openAnswerRepository.deleteAll();
        roomCrudRepository.deleteAll();
        roomTemplateCrudRepository.deleteAll();
        missionCrudRepository.deleteAll();
        studentCrudRepository.deleteAll();
        super.tearDown();
    }
}
