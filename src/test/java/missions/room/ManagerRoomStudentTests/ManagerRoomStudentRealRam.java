package missions.room.ManagerRoomStudentTests;

import Data.Data;
import DataAPI.Response;
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
import missions.room.Domain.Room;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.MissionManager;
import missions.room.Managers.StudentManager;
import missions.room.Managers.TeacherManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static Data.DataConstants.WRONG_TEACHER_NAME;
import static DataAPI.OpCode.DB_Error;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Service
public class ManagerRoomStudentRealRam extends ManagerRoomStudentAllStubs{

    protected Ram realRam;

    @Override
    protected void initMocks(){
        super.initMocks();
        realRam=new Ram();
        try {
            Field managerRam = StudentManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(managerRoomStudentWithMock,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(studentApiKey
                ,dataGenerator.getStudent(Data.VALID)
                        .getAlias());
        realRam.addApi(NULL_STUDENT_KEY
                ,WRONG_STUDENT_NAME);
        realRam.addAlias(studentApiKey);
        realRam.addApi(NULL_STUDENT_KEY
                ,WRONG_STUDENT_NAME);
        realRam.addApi(valid2StudentApiKey,
                dataGenerator.getStudent(Data.VALID2)
                        .getAlias());
        realRam.addAlias(valid2StudentApiKey);
        Room roomValid2StudentsFromDifferentGroups=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        realRam.addRoom(roomValid2StudentsFromDifferentGroups);
    }

//
//    @Override
//    void setUpMocks(){
//        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
//        classroomRepo=new ClassRoomRepositoryMock(dataGenerator);
//        studentCrudRepository=new StudentRepositoryMock(dataGenerator);
//        ram=new Ram();
//        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
//
//        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
//        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
//        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
//    }
//
//    @Override
//    void setUpWatchRoomDetails(){
//        super.setUpWatchRoomDetails();
//        ram.addApi("apiKey",dataGenerator.getStudent(Data.VALID).getAlias());
//    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
        realRam.clearRam();
    }
}
