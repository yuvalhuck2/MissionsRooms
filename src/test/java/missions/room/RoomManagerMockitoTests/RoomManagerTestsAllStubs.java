package missions.room.RoomManagerMockitoTests;


import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.RoomType;
import DataAPI.RoomsDataByRoomType;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Domain.Users.User;
import missions.room.Managers.RoomManager;
import missions.room.Repo.RoomRepo;
import missions.room.Repo.RoomTemplateRepo;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoomManagerTestsAllStubs {

    protected DataGenerator dataGenerator;

    @Mock
    protected RoomRepo roomRepoMock;

    @Mock
    protected RoomTemplateRepo roomTemplateRepoMock;

    @Mock
    protected TeacherRepo teacherRepoMock;

    protected String teacherApi;

    protected String studentApi;

    protected String roomId;

    private AutoCloseable closeable;

    protected List<User> users;

    protected Room room;

    @Mock
    protected Ram mockRam;

    @InjectMocks
    protected RoomManager roomManager;

    @BeforeEach
    void setUp() {
        dataGenerator = new DataGenerator();
        teacherApi = VALID_TEACHER_APIKEY;
        studentApi=VALID_STUDENT_APIKEY;
        initMocks();
    }

    protected void initMocks() {

        closeable= MockitoAnnotations.openMocks(this);
        Student student=dataGenerator.getStudent(Data.VALID);
        Teacher teacher=dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        room=dataGenerator.getRoom(Data.Valid_Student);
        roomId=room.getRoomId();

        initRam(teacher.getAlias());
        initTeacherRepo(teacher);
        initRoomRepo(teacher,student);

    }

    protected void initRam(String alias) {
        when(mockRam.getAlias(teacherApi))
                .thenReturn(alias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
        when(mockRam.getRoom(roomId))
                .thenReturn(room);
        when(mockRam.getRoom(WRONG_ROOM_ID))
                .thenReturn(null);
        when(mockRam.getAlias(VALID2_TEACHER_APIKEY))
                .thenReturn(dataGenerator.getTeacher(Data.VALID_WITHOUT_CLASSROOM).getAlias());
        when(mockRam.getAlias(VALID3_TEACHER_APIKEY))
                .thenReturn(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM2).getAlias());
    }

    protected void initTeacherRepo( Teacher teacher) {


        when(teacherRepoMock.findTeacherById(teacher.getAlias()))
                .thenReturn(new Response<>(teacher, OpCode.Success));

        when(teacherRepoMock.findTeacherById(dataGenerator.getTeacher(Data.VALID_WITHOUT_CLASSROOM).getAlias()))
                .thenReturn(new Response<>(dataGenerator.getTeacher(Data.VALID_WITHOUT_CLASSROOM), OpCode.Success));
        when(teacherRepoMock.findTeacherById(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(teacherRepoMock.findTeacherById(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM2).getAlias()))
                .thenReturn(new Response<>(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM2),OpCode.Success));



    }


    protected void initRoomRepo( Teacher teacher,Student student) {


        when(roomRepoMock.findRoomById(roomId)).
                thenReturn(new Response<>(room,OpCode.Success));
        when(roomRepoMock.findRoomById(WRONG_ROOM_ID))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(roomRepoMock.deleteRoom(room))
                .thenReturn(new Response<>(true,OpCode.Success));
        when(roomRepoMock.findClassroomRoomByAlias(teacher.getClassroom().getClassName()))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(roomRepoMock.findGroupRoomByAlias("g1"))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(roomRepoMock.findGroupRoomByAlias("g2"))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(roomRepoMock.findGroupRoomByAlias("g11"))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(roomRepoMock.findGroupRoomByAlias("g22"))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(roomRepoMock.findStudentRoomByAlias(student.getAlias()))
                .thenReturn(new Response<>((StudentRoom)room,OpCode.Success));
        when(roomRepoMock.findClassroomRoomByAlias(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM2).getClassroom().getClassName()))
                .thenReturn(new Response<>(null,OpCode.Success));

    }


    @Test
    protected void testCloseRoomConnectedStudents(){

        Response<Boolean> response=roomManager.closeRoom(teacherApi,room.getRoomId());
        assertFalse(response.getValue());
        assertEquals(response.getReason(),OpCode.CONNECTED_STUDENTS);
    }

    @Test
    protected void testCloseRoomHappy(){
        for(String connected:room.getConnectedUsersAliases()){
            room.disconnect(connected);
        }
        Response<Boolean> response=roomManager.closeRoom(teacherApi,room.getRoomId());
        assertTrue(response.getValue());
        assertEquals(response.getReason(),OpCode.Success);
    }

    @Test
    protected void testCloseRoomInvalidTeacher(){
        Response<Boolean> response=roomManager.closeRoom(INVALID_KEY,room.getRoomId());
        assertFalse(response.getValue());
        assertEquals(response.getReason(),OpCode.Wrong_Key);
    }

    @Test
    protected void testCloseRoomTeacherHasNoClassroom(){
        Response<Boolean> response=roomManager.closeRoom(VALID2_TEACHER_APIKEY,room.getRoomId());
        assertFalse(response.getValue());
        assertEquals(response.getReason(),OpCode.Teacher_Classroom_Is_Null);
    }

    @Test
    protected void testCloseRoomNotBelongToTeacher(){

        Response<Boolean> response=roomManager.closeRoom(VALID3_TEACHER_APIKEY,room.getRoomId());
        assertFalse(response.getValue());
        assertEquals(response.getReason(),OpCode.NOT_BELONGS_TO_TEACHER);
    }

    @Test
    protected void testWatchMyClassroomRoomsHappy(){
        Response<List<RoomsDataByRoomType>> response=roomManager.watchMyClassroomRooms(teacherApi);
        for(RoomsDataByRoomType room:response.getValue()){
            switch (room.getRoomType().name()){
                case "Personal":
                    assertEquals(room.getRoomDetailsDataList().size(),1);
                    assertEquals(room.getRoomDetailsDataList().get(0).getRoomId(),roomId);break;
                default:
                    assertEquals(room.getRoomDetailsDataList().size(),0);break;
            }
        }
        assertEquals(response.getReason(),OpCode.Success);
    }

    @Test
    protected void testWatchMyClassroomRoomsInvalidTeacher(){
        Response<List<RoomsDataByRoomType>> response=roomManager.watchMyClassroomRooms(WRONG_TEACHER_NAME);
        assertEquals(response.getValue(),null);
        assertEquals(response.getReason(),OpCode.Wrong_Key);
    }

    @Test
    protected void testWatchMyClassroomRoomsTeacherHasNoClassRoom(){
        Response<List<RoomsDataByRoomType>> response=roomManager.watchMyClassroomRooms(VALID2_TEACHER_APIKEY);
        assertEquals(response.getValue(),null);
        assertEquals(response.getReason(),OpCode.Teacher_Classroom_Is_Null);
    }


    @AfterEach
    void tearDown() {
        tearDownMocks();
    }

    protected void tearDownMocks(){
        try {
            closeable.close();
        } catch (Exception e) {
            fail("close mocks when don't need to");
        }
    }
}
