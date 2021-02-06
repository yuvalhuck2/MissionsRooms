package missions.room.RoomManagerTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.RoomManager;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomManagerRealRamTeacher extends RoomMangerTestsRealRam {

    @Autowired
    private TeacherRepo realTeacherRepo;

    @Override
    protected void initMocks(){
        super.initMocks();
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(roomMangerWithMockito,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    void setUpMocks(){
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
    }

    @Override
    void setUpAddRoom(){
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            ram=(Ram)managerRam.get(roomManger);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        ram.addApi(NULL_TEACHER_KEY
                ,WRONG_TEACHER_NAME);
        ram.addApi(SUPERVISOR_KEY,
                dataGenerator.getTeacher(Data.Supervisor)
                        .getAlias());
        super.setUpAddRoom();

    }

    @Test
    void testAddRoomInvalidWrongTeacher(){
        setUpAddRoom();
        //password for getting wrong alias
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.WRONG_NAME).getAlias());
        testAddRoomInValid(Data.VALID, OpCode.Not_Exist);
        tearDown();
    }

    @Test
    void getClassRoomDataNullClassroomTest(){
        setUpAddRoom();
        apiKey=SUPERVISOR_KEY;
        testGetClassRoomDataInvalid(OpCode.Supervisor);
        tearDown();
    }

    @Override
    @Test
    void getClassRoomFindTeacherByIdThrowsExceptionTest(){
        setUpAddRoom();
        when(mockTeacherCrudRepository.findById(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM)
                .getAlias()))
                .thenThrow(new RuntimeException());
        realTeacherRepo=new TeacherRepo(mockTeacherCrudRepository);
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(roomMangerWithMockito,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testGetClassRoomDataInvalid(OpCode.DB_Error);
        tearDown();
    }

    protected void tearDownMocks(){}

}
