//package missions.room.RoomManagerTests;
//
//import Data.Data;
//import DataAPI.OpCode;
//import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
//import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
//import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
//import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
//import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
//import missions.room.Domain.Ram;
//import missions.room.Managers.RoomManager;
//import missions.room.Managers.TeacherManager;
//import missions.room.RoomTemplateTests.RoomTemplateManagerTestsRealRam;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import java.lang.reflect.Field;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//@SpringBootTest
//@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
//public class RoomManagerRealRamTeacher extends RoomMangerTestsRealRam {
//
//    void setUpMocks(){
//        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
//        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
//        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
//    }
//
//    void setUpAddRoom(){
//        try {
//            Field managerRam = TeacherManager.class.getDeclaredField("ram");
//            managerRam.setAccessible(true);
//            ram=(Ram)managerRam.get(roomManger);
//
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            fail();
//        }
//        super.setUpAddRoom();
//
//    }
//
//    @Test
//    void testAddRoomInvalidWrongTeacher(){
//        setUpAddRoom();
//        //password for getting wrong alias
//        ram.addApi(apiKey,dataGenerator.getTeacher(Data.WRONG_NAME).getAlias());
//        testAddRoomInValid(Data.VALID, OpCode.Not_Exist);
//        tearDownAddRoom();
//    }
//}
