package missions.room.ManagerRoomStudentTests;


import CrudRepositories.RoomCrudRepository;
import Data.Data;
import DataAPI.Response;
import DataAPI.RoomDetailsData;
import missions.room.Domain.Room;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Repo.RoomRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static DataAPI.OpCode.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ManagerRoomStudentRealRoomRamStudentClassroomClassGroupRepo extends ManagerRoomStudentRealRamRealClassRoomRealRamStudentClassroomClassGroupRepo {

    @Autowired
    private RoomRepo realRoomRepo;

    @Autowired
    private RoomCrudRepository realRoomCrudRepository;

    @Mock
    private RoomCrudRepository mockRoomCrudRepository;

    @Override
    protected void initMocks(){
        super.initMocks();
        try {
            Field classroomRepo = ManagerRoomStudent.class.getDeclaredField("roomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,realRoomRepo);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic_All_Types));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Group));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID_2MissionStudent));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID_2Mission_Group));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID_2Mission_Class));

        Room studentRoom=dataGenerator.getRoom(Data.Valid_Student);
        Room groupRoom=dataGenerator.getRoom(Data.Valid_Group);
        Room classroomRoom = dataGenerator.getRoom(Data.Valid_Classroom);
        Room valid2MissionsStudentRoom =dataGenerator.getRoom(Data.VALID_2MissionStudent);
        Room valid2MissionsGroupRoom =dataGenerator.getRoom(Data.VALID_2Mission_Group);
        Room valid2MissionsClassRoom =dataGenerator.getRoom(Data.VALID_2Mission_Class);
        Room valid2StudentsFromDifferentGroups2Missions=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);

        realRoomCrudRepository.save(studentRoom);
        realRoomCrudRepository.save(groupRoom);
        realRoomCrudRepository.save(classroomRoom);
        realRoomCrudRepository.save(valid2MissionsStudentRoom);
        realRoomCrudRepository.save(valid2MissionsGroupRoom);
        realRoomCrudRepository.save(valid2MissionsClassRoom);
        realRoomCrudRepository.save(valid2StudentsFromDifferentGroups2Missions);

        //add the rooms to the ram to connect the students to the room
        realRam.addRoom(studentRoom);
        realRam.addRoom(groupRoom);
        realRam.addRoom(classroomRoom);
        realRam.addRoom(valid2MissionsStudentRoom);
        realRam.addRoom(valid2MissionsGroupRoom);
        realRam.addRoom(valid2MissionsClassRoom);
    }

//    @Override
//    void setUpMocks(){
//        ram=new Ram();
//        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
//    }

//    @Override
//    @Test
//    @Transactional
//    void testWatchRoomValid_2room(){
//        super.testWatchRoomValid_2room();
//    }

    private void setUpFindRoomThrowsEcxeption() {
        realRam.clearRooms();
        when(mockRoomCrudRepository.findById(any()))
                .thenThrow(new DataRetrievalFailureException(""));
        try {
            Field classroomRepo = ManagerRoomStudent.class.getDeclaredField("roomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,new RoomRepo(mockRoomCrudRepository));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    void testAnswerDeterministicSaveRoomThrowsException(){
        when(mockRoomCrudRepository.save(any()))
                .thenThrow(new DataRetrievalFailureException(""));
        try {
            Field classroomRepo = ManagerRoomStudent.class.getDeclaredField("roomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,new RoomRepo(mockRoomCrudRepository));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                DB_Error);
    }

    @Test
    @Override
    void testAnswerDeterministicDeleteRoomThrowsException(){
        Mockito.doThrow(new DataRetrievalFailureException("")).when(mockRoomCrudRepository).delete(any());
        try {
            Field classroomRepo = ManagerRoomStudent.class.getDeclaredField("roomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(managerRoomStudentWithMock,new RoomRepo(mockRoomCrudRepository));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Student).getRoomId(),
                DB_Error);
    }

    @Test
    @Override
    void testAnswerDeterministicFindRoomThrowsException(){
        setUpFindRoomThrowsEcxeption();
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                DB_Error);
    }

    @Test
    @Override
    public void testAnswerDeterministicHappyTest() {
        super.testAnswerDeterministicHappyTest();
        assertFalse(realRoomCrudRepository.existsById(dataGenerator.getRoom(Data.Valid_Student).getRoomId()));
    }

    @Test
    @Override
    void testAnswerDeterministic_wrongAnsTest() {
        super.testAnswerDeterministic_wrongAnsTest();
        assertFalse(realRoomCrudRepository.existsById(dataGenerator.getRoom(Data.Valid_Student).getRoomId()));
    }

    @Test
    @Override
    void testWatchRoomDataFindRoomByIdThrowsException(){
        setUpFindRoomThrowsEcxeption();
        testWatchRoomDataFailCase(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                DB_Error);
    }


    @Test
    @Override
    void testWatchRoomDataHappyTest(){
        Room room=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        super.testWatchRoomDataHappyTest();
        assertEquals(realRam.getRoom(room.getRoomId()).getMissionInCharge()
                ,dataGenerator.getStudent(Data.VALID)
                        .getAlias());
    }

    @Test
    @Override
    void testWatchRoomDataNotInChargeStudent(){
        Room room=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        super.testWatchRoomDataNotInChargeStudent();
        assertEquals(realRam.getRoom(room.getRoomId()).getMissionInCharge()
                ,dataGenerator.getStudent(Data.VALID)
                        .getAlias());
    }

    @Override
    @AfterEach
    void tearDown() {
        realRoomCrudRepository.deleteAll();
        roomTemplateCrudRepository.deleteAll();
        missionCrudRepository.deleteAll();
        super.tearDown();
    }
}
