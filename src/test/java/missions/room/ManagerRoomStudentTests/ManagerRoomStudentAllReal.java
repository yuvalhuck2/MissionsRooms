package missions.room.ManagerRoomStudentTests;


import CrudRepositories.RoomCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.APIObjects.SolutionData;
import Utils.Utils;
import missions.room.Domain.Classroom;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Users.Student;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Repo.OpenAnswerRepo;
import missions.room.Repo.RoomRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;

import static DataObjects.FlatDataObjects.OpCode.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ManagerRoomStudentAllReal extends ManagerRoomStudentRealRamRealClassRoomRealRamStudentClassroomClassGroupRepo {

    @Autowired
    private RoomRepo realRoomRepo;

    @Autowired
    private OpenAnswerRepo realOpenAnswerRepo;

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

            Field openAnswerRepo = ManagerRoomStudent.class.getDeclaredField("openAnswerRepo");
            openAnswerRepo.setAccessible(true);
            openAnswerRepo.set(managerRoomStudentWithMock,realOpenAnswerRepo);
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
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID_OPEN_ANS));


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
        realRoomCrudRepository.save(dataGenerator.getRoom(Data.VALID_OPEN_ANS));


        //add the rooms to the ram to connect the students to the room
        realRam.addRoom(studentRoom);
        realRam.addRoom(groupRoom);
        realRam.addRoom(classroomRoom);
        realRam.addRoom(valid2MissionsStudentRoom);
        realRam.addRoom(valid2MissionsGroupRoom);
        realRam.addRoom(valid2MissionsClassRoom);


    }

    @Override
    protected void initStoryRoom() {
        Room storyRoom = dataGenerator.getRoom(Data.VALID_STORY);
        String studentAlias=dataGenerator.getStudent(Data.VALID)
                .getAlias();
        String studentAlias2=dataGenerator.getStudent(Data.VALID2)
                .getAlias();

        super.initStoryRoom();

        missionCrudRepository.save(dataGenerator.getMission(Data.VALID_STORY));
        missionCrudRepository.save(dataGenerator.getMission(Data.VALID_STORY2));

        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID_STORY));

        realRoomCrudRepository.save(storyRoom);

        connectUserToRoom(studentApiKey,storyRoom.getRoomId(),studentAlias);
        connectUserToRoom(studentApiKey,storyRoom.getRoomId(),studentAlias2);

    }

    private void connectUserToRoom(String studentApiKey, String roomId, String studentAlias) {
        managerRoomStudentWithMock.watchRoomData(studentApiKey,roomId);
        realRam.connectToRoom(roomId,studentAlias);
    }

    @Override
    void setUpMocks(){
        ram=new Ram();
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
        ram.addApi("apiKey",dataGenerator.getStudent(Data.VALID).getAlias());
    }

    @Override
    @Test
    @Transactional
    void testWatchRoomValid_2room(){
        super.testWatchRoomValid_2room();
    }

    private void setUpFindRoomThrowsException() {
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
    void testAnswerDeterministicFindRoomThrowsException(){
        setUpFindRoomThrowsException();
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
        setUpFindRoomThrowsException();
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

    @Test
    @Override
    void testFinishStoryMissionHappyCase(){
        super.testFinishStoryMissionHappyCase();
        ClassroomRoom room= (ClassroomRoom) dataGenerator.getRoom(Data.VALID_STORY);
        final int pointOfMission=dataGenerator.getMission(Data.VALID_STORY).getPoints();
        Classroom classroom=classroomRepository.findById(room.getParticipant()
                .getClassName())
                .get();
        assertEquals(classroom.getPoints(),pointOfMission);
    }

    @Test
    @Override
    void testFinishStoryMissionFindRoomByIdThrowsException(){
        initStoryRoom();
        setUpFindRoomThrowsException();
        testInvalidFinishStoryMission(DB_Error);
    }

    @Test
    @Override
    void testAnswerStoryFindRoomByIdThrowsException(){
        initStoryRoom();
        setUpFindRoomThrowsException();
        testInvalidAnswerStory(DB_Error);
    }

    @Test
    @Override
    void testAnswerStoryNotEnoughConnected(){
        initStoryRoom();
        Student student = dataGenerator.getStudent(Data.VALID);
        Student student2 = dataGenerator.getStudent(Data.VALID2);
        Room room= dataGenerator.getRoom(Data.VALID_STORY);
        realRam.disconnectFromRoom(room.getRoomId(),student.getAlias());
        realRam.disconnectFromRoom(room.getRoomId(),student2.getAlias());
        testInvalidAnswerStory(Not_Enough_Connected);
    }

    @Test
    @Override
    void testAnswerOpenQuestionWithFileSuccess() {
        byte[] fileContent = "mock file".getBytes();
        String fileName = "file.txt";
        MultipartFile mockFile = new MockMultipartFile(fileName, fileName,"text/plain", fileContent);
        SolutionData solutionData = new SolutionData(dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId(), "ans");
        Response<Boolean> res = managerRoomStudentWithMock.answerOpenQuestionMission(studentApiKey, solutionData, mockFile);
        assertEquals(res.getReason(), OpCode.Success);
        assertTrue(res.getValue());
        assertTrue(checkOpenAnswerFileExist(dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId(), dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), fileName));
    }

    private boolean checkOpenAnswerFileExist(String roomId, String missionId, String fileName) {
        String rootDir = Utils.getRootDirectory();
        String filePath = rootDir+"/openAnswer/"+roomId+"/"+missionId+"/"+fileName;
        File f = new File(filePath);
        return f.exists();
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
