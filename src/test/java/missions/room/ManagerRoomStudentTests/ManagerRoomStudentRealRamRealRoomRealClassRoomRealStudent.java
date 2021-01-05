package missions.room.ManagerRoomStudentTests;


import Data.Data;
import RepositoryMocks.StudentRepositoryMock.StudentRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.ManagerRoomStudent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ManagerRoomStudentRealRamRealRoomRealClassRoomRealStudent extends ManagerRoomStudentRealRamRealClassRoomRealStudent {


    @Override
    void setUpMocks(){
        ram=new Ram();
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
    }

    @Override
    @Test
    @Transactional
    void testWatchRoomValid_2room(){
        super.testWatchRoomValid_2room();
    }



}
