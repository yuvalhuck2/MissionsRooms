package missions.room.ChatManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataObjects.APIObjects.ChatMessageData;
import DataObjects.APIObjects.UserChatData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Communications.Publisher.Publisher;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.ChatManager;
import missions.room.Repo.RoomRepo;
import missions.room.Repo.SchoolUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static Data.DataConstants.*;
import static DataObjects.FlatDataObjects.OpCode.DB_Error;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ChatManagerTestsAllStubs {

    protected static ChatMessageData MESSAGE = new ChatMessageData("hello",
            new UserChatData("id","name"),
            "21/08/2020",
            "messageId");

    @Mock
    private SchoolUserRepo mockSchoolUserRepo;

    @Mock
    private RoomRepo mockRoomRepo;

    @Mock
    private static Publisher mockPublisher;

    @Mock
    private Ram mockRam;

    @InjectMocks
    protected ChatManager chatManager;

    private AutoCloseable closeable;
    protected DataGenerator dataGenerator;
    protected String studentApiKey;
    protected String teacherApiKey;
    protected String roomId;
    protected Student student;
    protected Teacher teacher;
    protected Room room;

    @BeforeEach
    void setUp() {
        dataGenerator =new DataGenerator();
        studentApiKey = VALID_STUDENT_APIKEY;
        teacherApiKey = VALID_TEACHER_APIKEY;
        room = dataGenerator.getRoom(Data.Valid_Student);
        roomId = room.getRoomId();
        student = dataGenerator.getStudent(Data.VALID);
        teacher = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        room.connect(student.getAlias());
        initMocks();
    }

    private void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        initRam();
        initSchoolUserRepo();
        initRoomRepo();
        chatManager.initPublisher(mockPublisher);
    }

    protected void initRoomRepo() {
        when(mockRoomRepo.findRoomForRead(roomId))
                .thenReturn(new Response<>(room, OpCode.Success));
        when(mockRoomRepo.findRoomForRead(WRONG_ROOM_ID))
                .thenReturn(new Response<>(null, OpCode.Success));
    }

    protected void initSchoolUserRepo() {
        when(mockSchoolUserRepo.findUserForRead(teacher.getAlias()))
                .thenReturn(new Response<>(teacher, OpCode.Success));
        when(mockSchoolUserRepo.findUserForRead(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockSchoolUserRepo.findUserForRead(WRONG_USER_NAME))
                .thenReturn(new Response<>(null, OpCode.Success));
    }

    protected void initRam() {
        when(mockRam.getAlias(studentApiKey))
                .thenReturn(student.getAlias());
        when(mockRam.getApiKey(student.getAlias()))
                .thenReturn(studentApiKey);
        when(mockRam.getApiKey(teacher.getAlias()))
                .thenReturn(teacherApiKey);
        when(mockRam.getAlias(teacherApiKey))
                .thenReturn(teacher.getAlias());
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
        when(mockRam.connectToRoom(roomId,teacher.getAlias()))
                .thenReturn(OpCode.Teacher);
        when(mockRam.connectToRoom(roomId,student.getAlias()))
                .thenReturn(OpCode.IN_CHARGE);
        when(mockRam.isRoomExist(roomId))
                .thenReturn(false);
        when(mockRam.getRoom(roomId))
                .thenReturn(room);
    }

    @Test
    void enterChatHappyCase(){
        Response<String> chatResponse = chatManager.enterChat(teacherApiKey, roomId);
        assertEquals(chatResponse.getReason(), OpCode.Success);
        assertEquals(chatResponse.getValue(), teacher.getFullName());
    }

    @Test
    void enterChatNullKey(){
        teacherApiKey = null;
        enterChatInvalid(OpCode.Wrong_Key);
    }

    @Test
    void enterChatInvalidKey(){
        teacherApiKey = INVALID_KEY;
        enterChatInvalid(OpCode.Wrong_Key);
    }

    @Test
    void enterChatNotExistUser(){
        teacherApiKey = NULL_USER_KEY;
        enterChatInvalid(OpCode.Not_Exist);
    }

    @Test
    void enterChatSchoolUserRepoThrowsException(){
        when(mockSchoolUserRepo.findUserForRead(anyString()))
                .thenReturn(new Response<>(null, DB_Error));
        enterChatInvalid(OpCode.DB_Error);
    }


    void enterChatInvalid(OpCode opCode){
        Response<String> chatResponse = chatManager.enterChat(teacherApiKey, roomId);
        assertEquals(chatResponse.getReason(), opCode);
        assertNull(chatResponse.getValue());
    }

    @Test
    void sendMessageHappyCase(){
        Response<String> chatResponse = chatManager.sendMessage(teacherApiKey, MESSAGE ,roomId);
        assertEquals(chatResponse.getReason(), OpCode.Success);
        assertEquals(chatResponse.getValue(), "");
        verify(mockPublisher,times(1)).update(eq(studentApiKey),any());
    }

    @Test
    void sendMessageInvalidKey(){
        teacherApiKey = INVALID_KEY;
        sendMessageInvalid(OpCode.Wrong_Key);
    }

    @Test
    void sendMessageRoomNotExist(){
        roomId = WRONG_ROOM_ID;
        sendMessageInvalid(OpCode.Not_Exist_Room);
    }

    @Test
    void sendMessageRoomRepoFindRoomForReadThrowsException(){
        when(mockRoomRepo.findRoomForRead(anyString()))
                .thenReturn(new Response<>(null, DB_Error));
        sendMessageInvalid(OpCode.DB_Error);
    }

    protected void sendMessageInvalid(OpCode opCode){
        Response<String> chatResponse = chatManager.sendMessage(teacherApiKey, MESSAGE ,roomId);
        assertEquals(chatResponse.getReason(), opCode);
        assertEquals(chatResponse.getValue(), "");
        verify(mockPublisher,times(0)).update(eq(studentApiKey),any());
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
