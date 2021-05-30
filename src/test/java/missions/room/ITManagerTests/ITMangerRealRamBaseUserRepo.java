package missions.room.ITManagerTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.ITCrudRepository;
import CrudRepositories.UserCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import missions.room.Domain.Users.IT;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.ITManager;
import missions.room.Repo.ClassroomRepo;
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
    protected UserCrudRepository userCrudRepository;

    @Autowired
    protected ClassroomRepository classroomRepository;

    @Override
    protected void initUserRepo(IT it, String itAlias2) {
        itCrudRepository.save(it);
        classroomRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        realUserRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM3));
        realUserRepo.save(dataGenerator.getTeacher(Data.VALID_WITHOUT_CLASSROOM));

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
    @Test
    void testDeleteUserTeacherHasClassroom() {
        userCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        super.testDeleteUserTeacherHasClassroom();
    }

    @Override
    @AfterEach
    void tearDown() {
        userCrudRepository.deleteAll();
        classroomRepository.deleteAll();
        super.tearDown();
    }
}
