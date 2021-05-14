package missions.room.ManagerRoomStudentTests;

import CrudRepositories.StudentCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.RoomDetailsData;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.StudentManager;
import missions.room.Repo.StudentRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Optional;

import static Data.DataConstants.VALID2_STUDENT_APIKEY;
import static DataObjects.FlatDataObjects.OpCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ManagerRoomStudentRealRamRealClassRoomRealRamStudentClassroomClassGroupRepo extends ManagerRoomStudentRealRamRealClassroomClassGroupRepo {

    @Autowired
    protected StudentRepo realStudentRepo;

    @Mock
    private StudentCrudRepository mockStudentCrudRepository;

    @Override
    protected void initMocks(){
        super.initMocks();
        try {
            Field classroomRepo = StudentManager.class.getDeclaredField("studentRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,realStudentRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    protected void initStoryRoom() {
        realStudentRepo.save((missions.room.Domain.Users.Student) dataGenerator.getUser(Data.VALID_STUDENT));
        super.initStoryRoom();
    }

    @Override
    void setUpMocks(){
        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
        ram=new Ram();
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
        ram.addApi("apiKey",dataGenerator.getStudent(Data.VALID).getAlias());
    }

    private void setUpStudentFindByIdThrowsException() {
        when(mockStudentCrudRepository.findById(any()))
                .thenThrow(new DataRetrievalFailureException(""));
        try {
            Field classroomRepo = StudentManager.class.getDeclaredField("studentRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,new StudentRepo(mockStudentCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    void testAnswerDeterministicSaveStudentThrowsException(){
        when(mockStudentCrudRepository.findById(any()))
                .thenReturn(Optional.of(dataGenerator.getStudent(Data.VALID)));
        when(mockStudentCrudRepository.save(any()))
                .thenThrow(new DataRetrievalFailureException(""));
        try {
            Field classroomRepo = StudentManager.class.getDeclaredField("studentRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,new StudentRepo(mockStudentCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Student).getRoomId(),
                DB_Error);
    }

    @Test
    @Override
    void testAnswerDeterministicStudentFindByIdThrowsException(){
        setUpStudentFindByIdThrowsException();
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.DB_Error);
    }

    @Test
    @Override
    public void testAnswerDeterministicHappyTest() {
        int points=dataGenerator.getStudent(Data.VALID).getPoints();
        super.testAnswerDeterministicHappyTest();
        Room room=dataGenerator.getRoom(Data.Valid_Student);
        points += room.getRoomTemplate().getMission(0).getPoints();
        int bonus=room.getBonus();
        assertEquals(studentCrudRepository
                        .findById(dataGenerator.getStudent(Data.VALID).getAlias())
                        .get()
                        .getPoints()
                ,points+bonus);
    }

    @Test
    @Override
    void testAnswerDeterministic_wrongAnsTest() {
        super.testAnswerDeterministic_wrongAnsTest();
        assertEquals(studentCrudRepository
                        .findById(dataGenerator.getStudent(Data.VALID).getAlias())
                        .get()
                        .getPoints()
                ,dataGenerator.getStudent(Data.VALID).getPoints());
    }

    @Test
    @Override
    void testAnswerDeterministic_2MissionsRoomStudentTest() {
        int points=dataGenerator.getStudent(Data.VALID).getPoints();
        super.testAnswerDeterministic_2MissionsRoomStudentTest();
        Room room=dataGenerator.getRoom(Data.VALID_2MissionStudent);
        points += room.getRoomTemplate().getMission(0).getPoints();
        assertEquals(studentCrudRepository
                        .findById(dataGenerator.getStudent(Data.VALID).getAlias())
                        .get()
                        .getPoints()
                ,points);
    }

    @Test
    @Override
    void testWatchRoomDataStudentFindByIdThrowsException(){
        setUpStudentFindByIdThrowsException();
        testWatchRoomDataFailCase(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.DB_Error);
    }

    @Test
    void testWatchRoomDataNotBelongToRoomStudent(){
        testWatchRoomDataFailCase(VALID2_STUDENT_APIKEY,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                NOT_BELONGS_TO_ROOM);
    }

    @Test
    void testWatchRoomDataNotInChargeStudent(){
        Room room=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        managerRoomStudentWithMock.watchRoomData(studentApiKey,
                room.getRoomId());
        Response<RoomDetailsData> roomDetailsDataResponse=managerRoomStudentWithMock.watchRoomData(valid2StudentApiKey,
                room.getRoomId());
        Assert.assertEquals(roomDetailsDataResponse.getReason(), NOT_IN_CHARGE);
        Assert.assertEquals(roomDetailsDataResponse.getValue(),room.getData());
    }

    @Test
    @Override
    void testAnswerStoryNotBelongToRoom(){
        initStoryRoom();
        studentApiKey=thirdStudentKey;
        testInvalidAnswerStory(NOT_BELONGS_TO_ROOM);
    }

    @Test
    @Override
    void testAnswerStoryFindStudentThrowsException(){
        initStoryRoom();
        setUpStudentFindByIdThrowsException();
        testInvalidAnswerStory(DB_Error);
    }

    @Test
    @Override
    void testFinishStoryMissionNotBelongToRoom(){
        initStoryRoom();
        studentApiKey=thirdStudentKey;
        testInvalidFinishStoryMission(NOT_BELONGS_TO_ROOM);
    }

    @Test
    @Override
    void testAFinishStoryMissionFindStudentThrowsException(){
        initStoryRoom();
        setUpStudentFindByIdThrowsException();
        testInvalidFinishStoryMission(DB_Error);
    }

}
