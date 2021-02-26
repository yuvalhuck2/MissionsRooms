package missions.room.ManagerRoomStudentTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.GroupRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.StudentRepositoryMock.StudentRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Repo.ClassGroupRepo;
import missions.room.Repo.ClassroomRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static DataAPI.OpCode.DB_Error;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ManagerRoomStudentRealRamRealClassroomClassGroupRepo extends ManagerRoomStudentRealRam {

    @Autowired
    private ClassroomRepo realClassroomRepo;

    @Autowired
    private ClassGroupRepo realClassGroupRepo;

    @Autowired
    protected ClassroomRepository classroomRepository;

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Mock
    private ClassroomRepository mockClassroomRepository;

    @Mock
    private GroupRepository mockGroupRepository;

    @Override
    void setUpMocks(){
        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
        studentCrudRepository=new StudentRepositoryMock(dataGenerator);
        ram=new Ram();
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
        ram.addApi("apiKey",dataGenerator.getStudent(Data.VALID).getAlias());
    }

    @Override
    protected void initMocks(){
        super.initMocks();
        try {
            Field classroomRepo = ManagerRoomStudent.class.getDeclaredField("classroomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,realClassroomRepo);
            Field classGroupRepo = ManagerRoomStudent.class.getDeclaredField("classGroupRepo");
            classGroupRepo.setAccessible(true);
            classGroupRepo.set(managerRoomStudentWithMock,realClassGroupRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        classroomRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        classroomRepository.save(dataGenerator.getTeacher(Data.Valid_2Students_From_Different_Groups).getClassroom());
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.Valid_2Students_From_Different_Groups));
        when(mockClassroomRepository.save(any()))
                .thenThrow(new DataRetrievalFailureException(""));
        when(mockGroupRepository.save(any()))
                .thenThrow(new DataRetrievalFailureException(""));
    }

    @Test
    @Override
    void testAnswerDeterministicSaveGroupThrowsException(){
        try {
            Field classGroupRepo = ManagerRoomStudent.class.getDeclaredField("classGroupRepo");
            classGroupRepo.setAccessible(true);
            classGroupRepo.set(managerRoomStudentWithMock,new ClassGroupRepo(mockGroupRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Group).getRoomId(),
                DB_Error);
    }

    @Test
    @Override
    void testAnswerDeterministicSaveClassThrowsException(){
        try {
            Field classroomRepo = ManagerRoomStudent.class.getDeclaredField("classroomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,new ClassroomRepo(mockClassroomRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Classroom).getRoomId(),
                DB_Error);
    }

    @Test
    @Override
    void testAnswerDeterministic_2MissionsRoomGroupTest() {
        super.testAnswerDeterministic_2MissionsRoomGroupTest();
        Room room=dataGenerator.getRoom(Data.VALID_2Mission_Group);
        int points=room.getRoomTemplate().getMission(0).getPoints();
        assertEquals(groupRepository
                        .findById(dataGenerator.getClassGroup(Data.Valid_Group).getGroupName())
                        .get()
                        .getPoints()
                ,points);
    }

    @Test
    @Override
    void testAnswerDeterministic_2MissionsRoomClassTest() {
        super.testAnswerDeterministic_2MissionsRoomClassTest();
        Room room=dataGenerator.getRoom(Data.VALID_2Mission_Class);
        int points=room.getRoomTemplate().getMission(0).getPoints();
        assertEquals(classroomRepository
                        .findById(dataGenerator.getClassroom(Data.Valid_Classroom).getClassName())
                        .get()
                        .getPoints()
                ,points);
    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
        teacherCrudRepository.deleteAll();
        classroomRepository.deleteAll();
    }
}
