package missions.room.ManagerRoomStudentTests;

import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.StudentRepositoryMock.StudentRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.ManagerRoomStudent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ManagerRoomStudentRealRamRealClassRoomRealStudent extends ManagerRoomStudentRealRamRealClassRoom{

    @Override
    void setUpMocks(){
        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
        //studentCrudRepository=new StudentRepositoryMock(dataGenerator);
        ram=new Ram();
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo);
    }
}
