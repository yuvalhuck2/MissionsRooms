package missions.room.ITManagerTests;

import CrudRepositories.ITCrudRepository;
import CrudRepositories.UserCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import missions.room.Domain.Users.IT;
import missions.room.Managers.ITManager;
import missions.room.Repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ITMangerRealRamBaseUserRepo extends ITManagerTestsRealRam {

    @Autowired
    private UserRepo realUserRepo;

    @Autowired
    private ITCrudRepository itCrudRepository;

    @Mock
    private UserCrudRepository mockCrudRepository;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    protected void initUserRepo(IT it, String itAlias2) {
        itCrudRepository.save(it);
        try {
            Field userRepo = ITManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(itManager,realUserRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    private void setUpUserRepoIsExistByIdThrowsException(){
        when(mockCrudRepository.existsById(anyString()))
                .thenThrow(new RuntimeException());
        try {
            Field userRepo = ITManager.class.getDeclaredField("userRepo");
            userRepo.setAccessible(true);
            userRepo.set(itManager,new UserRepo(mockCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void addNewITUserRepoIsExistsByIdThrowsException(){
        setUpUserRepoIsExistByIdThrowsException();
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    @Override
    void testAddStudentUserRepoIsExistsByIdThrowsException(){
        setUpUserRepoIsExistByIdThrowsException();
        testAddStudentInvalid(OpCode.DB_Error);
    }

    @Test
    void testAddTeacherUserRepoIsExistsByIdThrowsException(){
        setUpUserRepoIsExistByIdThrowsException();
        testAddTeacherInvalid(OpCode.DB_Error);
    }

    @Override
    @AfterEach
    void tearDown() {
        userCrudRepository.deleteAll();
        super.tearDown();
    }
}
