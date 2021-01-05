package missions.room.ManagerRoomStudentTests;

import Data.Data;
import DomainMocks.MissionMock;
import DomainMocks.MockRam;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.StudentRepositoryMock.StudentRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.MissionManager;
import org.springframework.stereotype.Service;

@Service
public class ManagerRoomStudentRealRam extends ManagerRoomStudentAllStubs{

    @Override
    void setUpMocks(){
        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
        classroomRepo=new ClassRoomRepositoryMock(dataGenerator);
        studentCrudRepository=new StudentRepositoryMock(dataGenerator);
        ram=new Ram();
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo);

        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
    }

    @Override
    void setUpWatchRoomDetails(){
        super.setUpWatchRoomDetails();
        ram.addApi("apiKey",dataGenerator.getStudent(Data.VALID).getAlias());



    }





}
