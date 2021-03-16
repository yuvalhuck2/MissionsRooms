package missions.room.ProfileMessagesManagerTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.StudentCrudRepository;
import CrudRepositories.UserCrudRepository;
import Data.Data;
import DataAPI.MessageData;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.User;
import missions.room.Managers.ProfileMessagesManager;
import missions.room.Repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ProfileProfileProfileMessageManagerRealRamUserRepo extends ProfileProfileMessageManagerTestsRealRam {

    @Autowired
    private UserRepo realUserRepo;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    protected StudentCrudRepository studentCrudRepository;

    @Mock
    private UserCrudRepository mockUserCrudRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    protected void initUserRepo(Student student, Student student2) {
        User teacher = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        User it = dataGenerator.getUser(Data.VALID_IT);
        users = new ArrayList<>();
        Collections.addAll(users,student,student2,teacher,it);
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        studentCrudRepository.save(student2);
        userCrudRepository.save(teacher);
        userCrudRepository.save(it);
        try {
            Field userRepo = ProfileMessagesManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(profileMessagesManager,realUserRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    private void setUpUserRepoThrowsException() {
        try {
            Field userRepo = ProfileMessagesManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(profileMessagesManager,new UserRepo(mockUserCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    void testSendMessageFindUserForWriteThrowsException(){
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.findUserForWrite(anyString()))
                .thenThrow(new RuntimeException());
        when(mockUserCrudRepository.existsById(anyString()))
                .thenReturn(true);
        testInvalidAddMessage(OpCode.DB_Error);
    }

    @Test
    @Transactional
    void testSendMessageSaveUserThrowsException(){
        setUpUserRepoThrowsException();
        String alias=dataGenerator.getStudent(Data.VALID2).getAlias();
        when(mockUserCrudRepository.save(any()))
                .thenThrow(new RuntimeException());
        when(mockUserCrudRepository.existsById(anyString()))
                .thenReturn(true);
        when(mockUserCrudRepository.findUserForWrite(alias))
                .thenReturn(userCrudRepository.findUserForWrite(alias));
        testInvalidAddMessage(OpCode.DB_Error);
    }

    @Test
    void testSendMessageIsExistByIdThrowsException(){
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.existsById(anyString()))
                .thenThrow(new RuntimeException());
        testInvalidAddMessage(OpCode.DB_Error);
    }

    @Test
    void viewMessagesFindUserThrowsException(){
        initAddMessage();
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.findById(anyString()))
                .thenThrow(new RuntimeException());
        Response<List<MessageData>> response= profileMessagesManager.viewMessages(student2ApiKey);
        assertEquals(response.getReason(),OpCode.DB_Error);
        assertNull(response.getValue());
    }

    @Test
    @Transactional
    void deleteMessageSaveThrowsException(){
        initAddMessage();
        setUpUserRepoThrowsException();
        String alias=dataGenerator.getStudent(Data.VALID2).getAlias();
        when(mockUserCrudRepository.save(any()))
                .thenThrow(new RuntimeException());
        when(mockUserCrudRepository.findUserForWrite(alias))
                .thenReturn(userCrudRepository.findUserForWrite(alias));
        testInvalidDeleteMessage(OpCode.DB_Error);
    }

    @Test
    void deleteMessageFindUserForWriteThrowsException(){
        initAddMessage();
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.findUserForWrite(anyString()))
                .thenThrow(new RuntimeException());
        testInvalidDeleteMessage(OpCode.DB_Error);
    }

    @Test
    void testWatchProfileIsExistByIdThrowsException(){
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.existsById(anyString()))
                .thenThrow(new RuntimeException());
        testWatchProfileInvalid(OpCode.DB_Error);
    }

    @Override
    @AfterEach
    void tearDown() {
        studentCrudRepository.deleteAll();
        userCrudRepository.deleteAll();
        super.tearDown();
    }

}
