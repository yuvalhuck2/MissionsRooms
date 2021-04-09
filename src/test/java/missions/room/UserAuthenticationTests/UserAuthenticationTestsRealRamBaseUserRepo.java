package missions.room.UserAuthenticationTests;

import CrudRepositories.UserCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import missions.room.Domain.Users.Student;
import missions.room.Managers.UserAuthenticationManager;
import missions.room.Repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class UserAuthenticationTestsRealRamBaseUserRepo extends BaseUserAuthenticationTestRealRam {

    @Autowired
    private UserRepo realUserRepo;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Mock
    private UserCrudRepository mockUserCrudRepository;

    @Override
    protected void initUserRepo(Student student) {
        userCrudRepository.save(student);
        try {
            Field userRepo = UserAuthenticationManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,realUserRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    private void setUpUserRepoThrowsException() {
        try {
            Field userRepo = UserAuthenticationManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,new UserRepo(mockUserCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testChangePasswordFindUserForWriteThrowsException(){
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.findUserForWrite(anyString())).
                thenThrow(new RuntimeException());
        testInvalidChangePassword(OpCode.DB_Error);
    }

    @Test
    void testInvalidChangePasswordSaveThrowsException(){
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.findUserForWrite(anyString())).
                thenReturn(userCrudRepository.findById(dataGenerator.
                                getStudent(Data.VALID)
                                .getAlias()).get());
        when(mockUserCrudRepository.save(any())).
                thenThrow(new RuntimeException());
        testInvalidChangePassword(OpCode.DB_Error);
    }

    @Test
    void testResetPasswordUserRepoIsExistThrowsException(){
        setUpUserRepoThrowsException();
        when(mockUserCrudRepository.existsById(anyString()))
                .thenThrow(new RuntimeException());
        testResetPasswordInvalid(OpCode.DB_Error);
    }

    @Override
    protected void tearDownMocks() {
        userCrudRepository.deleteAll();
        super.tearDownMocks();
    }
}
