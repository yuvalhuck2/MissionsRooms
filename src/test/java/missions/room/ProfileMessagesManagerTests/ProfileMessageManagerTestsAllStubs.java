package missions.room.ProfileMessagesManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.MessageData;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.UserProfileData;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.User;
import missions.room.Managers.ProfileMessagesManager;
import missions.room.Repo.UserRepo;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Data.DataConstants.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProfileMessageManagerTestsAllStubs {

    protected DataGenerator dataGenerator;

    protected String studentApiKey;

    protected String student2ApiKey;

    protected List<User> users;

    @InjectMocks
    protected ProfileMessagesManager profileMessagesManager;

    @Mock
    protected Ram mockRam;

    @Mock
    protected UserRepo mockUserRepo;

    private AutoCloseable closeable;

    private String message;

    protected String targetAlias;

    private String messageId;

    @BeforeEach
    void setUp() {
        dataGenerator = new DataGenerator();
        studentApiKey = VALID_STUDENT_APIKEY;
        student2ApiKey = VALID2_STUDENT_APIKEY;
        message = dataGenerator.getMessageData(Data.VALID).getMessage();
        initMocks();
    }

    protected void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        Student student=dataGenerator.getStudent(Data.VALID);
        Student student2=dataGenerator.getStudent(Data.VALID2);
        targetAlias =student2.getAlias();
        initRam(student.getAlias());
        initUserRepo(student,student2);
    }

    protected void initAddMessage(){
        MessageData messageData=dataGenerator.getMessageData(Data.VALID);
        profileMessagesManager.sendMessage(studentApiKey,messageData.getMessage(), targetAlias);
        messageId= profileMessagesManager.viewMessages(student2ApiKey).
                getValue().
                iterator().
                next().
                getId();
    }

    protected void initUserRepo(Student student, Student student2) {
        User teacher = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        User it = dataGenerator.getUser(Data.VALID_IT);
        users = new ArrayList<>();
        Collections.addAll(users,student,student2,teacher,it);

        when(mockUserRepo.isExistsById(student.getAlias()))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockUserRepo.isExistsById(WRONG_USER_NAME))
                .thenReturn(new Response<>(false,OpCode.Success));
        when(mockUserRepo.save(any())).
                thenReturn(new Response<>(student2,OpCode.Success));
        when(mockUserRepo.findUserForWrite(targetAlias))
                .thenReturn(new Response<>(student2,OpCode.Success));
        when(mockUserRepo.findUserForWrite(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(mockUserRepo.findUser(targetAlias))
                .thenReturn(new Response<>(student2,OpCode.Success));
        when(mockUserRepo.findUser(WRONG_USER_NAME)).
                thenReturn(new Response<>(null,OpCode.Not_Exist));
        when(mockUserRepo.findUser(WRONG_USER_NAME)).
                thenReturn(new Response<>(null,OpCode.Not_Exist));
        when(mockUserRepo.findUser(teacher.getAlias())).
                thenReturn(new Response<>(teacher,OpCode.Success));
        when(mockUserRepo.findUser(it.getAlias())).
                thenReturn(new Response<>(it,OpCode.Success));
        when(mockUserRepo.findAllUsers())
                .thenReturn(new Response<>(users,OpCode.Success));
    }

    protected void initRam(String alias) {
        when(mockRam.getAlias(studentApiKey))
                .thenReturn(alias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
        when(mockRam.getAlias(student2ApiKey)).
                thenReturn(targetAlias);
    }

    @Test
    void testSendMessageHappyCase(){
        Response<Boolean> response = profileMessagesManager.sendMessage(studentApiKey,message, targetAlias);
        assertTrue(response.getValue());
        assertEquals(response.getReason(),OpCode.Success);
    }

    @Test
    void testSendMessageEmptyMessage(){
        message="";
        testInvalidAddMessage(OpCode.Empty);
    }

    @Test
    void testSendMessageNullMessage(){
        message=null;
        testInvalidAddMessage(OpCode.Empty);
    }

    @Test
    void testSendMessageNotExistUser(){
        studentApiKey=NULL_USER_KEY;
        testInvalidAddMessage(OpCode.Not_Exist);
    }

    @Test
    void testSendMessageIsExistByIdThrowsException(){
        when(mockUserRepo.isExistsById(anyString()))
                .thenReturn(new Response<>(false,OpCode.DB_Error));
        testInvalidAddMessage(OpCode.DB_Error);
    }

    @Test
    void testSendMessageFindUserForWriteThrowsException(){
        when(mockUserRepo.findUserForWrite(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testInvalidAddMessage(OpCode.DB_Error);
    }

    @Test
    void testSendMessageSaveUserThrowsException(){
        when(mockUserRepo.save(any()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        testInvalidAddMessage(OpCode.DB_Error);
    }

    @Test
    void testSendMessageNotExistOtherUser(){
        targetAlias=WRONG_USER_NAME;
        when(mockUserRepo.findUserForWrite(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Not_Exist));
        testInvalidAddMessage(OpCode.Not_Exist);
    }

    protected void testInvalidAddMessage(OpCode opCode) {
        Response<Boolean> response = profileMessagesManager.sendMessage(studentApiKey,message, targetAlias);
        assertFalse(response.getValue());
        assertEquals(response.getReason(),opCode);
    }

    @Test
    void viewMessagesHappyTest(){
        initAddMessage();
        MessageData messageData=dataGenerator.getMessageData(Data.VALID);
        Response<List<MessageData>> response= profileMessagesManager.viewMessages(student2ApiKey);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().iterator().next(), messageData);
    }

    @Test
    void viewMessagesUserNotExist(){
        initAddMessage();
        student2ApiKey=NULL_USER_KEY;
        Response<List<MessageData>> response= profileMessagesManager.viewMessages(student2ApiKey);
        assertEquals(response.getReason(),OpCode.Not_Exist);
        assertNull(response.getValue());
    }

    @Test
    void viewMessagesFindUserThrowsException(){
        initAddMessage();
        when(mockUserRepo.findUser(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        Response<List<MessageData>> response= profileMessagesManager.viewMessages(student2ApiKey);
        assertEquals(response.getReason(),OpCode.DB_Error);
        assertNull(response.getValue());
    }

    @Test
    void deleteMessageHappyCase(){
        initAddMessage();
        Response<Boolean> response= profileMessagesManager.deleteMessage(student2ApiKey,messageId);
        assertEquals(response.getReason(),OpCode.Success);
        assertTrue(response.getValue());
    }

    @Test
    void deleteMessageUserNotExist(){
        initAddMessage();
        student2ApiKey=NULL_USER_KEY;
        testInvalidDeleteMessage(OpCode.Not_Exist);
    }

    @Test
    void deleteMessageFindUserForWriteThrowsException(){
        initAddMessage();
        when(mockUserRepo.findUserForWrite(anyString())).
                thenReturn(new Response<>(null,OpCode.DB_Error));
        testInvalidDeleteMessage(OpCode.DB_Error);
    }

    @Test
    void deleteMessageNotExistMessage(){
        testInvalidDeleteMessage(OpCode.Message_Not_Exist);
    }

    @Test
    void deleteMessageSaveThrowsException(){
        initAddMessage();
        when(mockUserRepo.save(any())).
                thenReturn(new Response<>(null,OpCode.DB_Error));
        testInvalidDeleteMessage(OpCode.DB_Error);
    }

    protected void testInvalidDeleteMessage(OpCode opCode) {
        Response<Boolean> response= profileMessagesManager.deleteMessage(student2ApiKey,messageId);
        assertEquals(response.getReason(),opCode);
        assertFalse(response.getValue());
    }

    @Test
    void testWatchProfileHappyCase(){
        List<UserProfileData> userProfileDataList=new ArrayList<>();
        for( User u: users)
            userProfileDataList.add(u.getProfileData());
        Response<List<UserProfileData>> userResponse = profileMessagesManager.watchProfile(studentApiKey);
        assertEquals(userResponse.getReason(),OpCode.Success);
        assertEquals(userResponse.getValue(),userProfileDataList);
    }

    @Test
    void testWatchProfileNotExistUser(){
        studentApiKey=NULL_USER_KEY;
        testWatchProfileInvalid(OpCode.Not_Exist);
    }

    @Test
    void testWatchProfileIsExistByIdThrowsException(){
        when(mockUserRepo.isExistsById(anyString()))
                .thenReturn(new Response<>(false,OpCode.DB_Error));
        testWatchProfileInvalid(OpCode.DB_Error);
    }

    protected void testWatchProfileInvalid(OpCode opCode){
        Response<List<UserProfileData>> userResponse = profileMessagesManager.watchProfile(studentApiKey);
        assertEquals(userResponse.getReason(),opCode);
        assertNull(userResponse.getValue());
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
