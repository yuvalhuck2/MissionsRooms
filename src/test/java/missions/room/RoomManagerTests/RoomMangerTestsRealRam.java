package missions.room.RoomManagerTests;

import Data.Data;
import DataAPI.OpCode;
import Data.DataGenerator;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.RoomManager;
import missions.room.Managers.TeacherManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.fail;


public class RoomMangerTestsRealRam extends RoomManagerTestsAllStubs{

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }


    @Override
    protected void initMocks() {
        Ram realRam=new Ram();
        super.initMocks();
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(roomMangerWithMockito,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(NULL_TEACHER_KEY,WRONG_TEACHER_NAME);
    }
    void setUpMocks(){
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        classroomRepository=new ClassRoomRepositoryMock(dataGenerator);
        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
    }

    @Override
    void setUpAddRoom() {
        super.setUpAddRoom();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.Supervisor));
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias());
    }

    @Test
    void testAddRoomInvalidWrongApiKey(){
        setUpAddRoom();
        apiKey=INVALID_KEY;
        testAddRoomInValid(Data.VALID, OpCode.Wrong_Key);
        tearDown();
    }

}
