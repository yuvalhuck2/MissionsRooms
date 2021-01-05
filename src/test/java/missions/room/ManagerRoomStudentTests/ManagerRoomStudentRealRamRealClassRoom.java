package missions.room.ManagerRoomStudentTests;

import Data.Data;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.StudentRepositoryMock.StudentRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.ManagerRoomStudent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ManagerRoomStudentRealRamRealClassRoom extends ManagerRoomStudentRealRam {

    @Override
    void setUpMocks(){
        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
        //classroomRepo=new ClassRoomRepositoryMock(dataGenerator);
        studentCrudRepository=new StudentRepositoryMock(dataGenerator);
        ram=new Ram();
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
    }


}
