package missions.room.ITManagerTests;

import CrudRepositories.ITCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import missions.room.Domain.Users.IT;
import missions.room.Managers.ITManager;
import missions.room.Repo.ITRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ITMangerTestsRealRamUserITRepo extends ITMangerRealRamUserRepo {

    @Autowired
    private ITRepo realITRepo;

    @Autowired
    private ITCrudRepository itCrudRepository;

    @Mock
    private ITCrudRepository mockItCrudRepository;

    private void setUpITRepoFindByIdThrowsException(){
        when(mockItCrudRepository.findById(anyString()))
                .thenThrow(new RuntimeException());
        try {
            Field itRepo = ITManager.class.getDeclaredField("itRepo");
            itRepo.setAccessible(true);
            itRepo.set(itManager,new ITRepo(mockItCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    protected void initITRepo(IT it, String itAlias, IT it2) {
        try {
            Field itRepo = ITManager.class.getDeclaredField("itRepo");
            itRepo.setAccessible(true);
            itRepo.set(itManager,realITRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    @Test
    void addNewITHappyCase() {
        super.addNewITHappyCase();
        IT it2= (IT) dataGenerator.getUser(Data.VALID_IT2);
        Optional<IT> itOptional= itCrudRepository.findById(it2.getAlias());
        assertTrue(itOptional.isPresent());
        assertEquals(it2,itOptional.get());
    }

    @Test
    @Override
    void addNewITITRepoSaveThrowsException(){
        String alias=dataGenerator.getUser(Data.VALID_IT).getAlias();
        when(mockItCrudRepository.findById(anyString()))
                .thenReturn(itCrudRepository.findById(alias));
        when(mockItCrudRepository.save(any()))
                .thenThrow(new RuntimeException());
        try {
            Field itRepo = ITManager.class.getDeclaredField("itRepo");
            itRepo.setAccessible(true);
            itRepo.set(itManager,new ITRepo(mockItCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    @Override
    void addNewITITRepoFindByIdThrowsException(){
        setUpITRepoFindByIdThrowsException();
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Override
    protected void addNewITInvalid(OpCode opCode, RegisterDetailsData registerDetailsData) {
        super.addNewITInvalid(opCode, registerDetailsData);
        IT it2= (IT) dataGenerator.getUser(Data.VALID_IT2);
        Optional<IT> itOptional= itCrudRepository.findById(it2.getAlias());
        assertFalse(itOptional.isPresent());
    }

    @Test
    @Override
    void updateUserDetailsTRepoFindByIdThrowsException(){
        when(mockItCrudRepository.findById(anyString()))
                .thenThrow(new RuntimeException());
        try {
            Field itRepo = ITManager.class.getDeclaredField("itRepo");
            itRepo.setAccessible(true);
            itRepo.set(itManager,new ITRepo(mockItCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        updateUserDetailsInvalidTest(OpCode.DB_Error);
    }

    @Test
    void testAddStudentRepoFindByIdThrowsException(){
        setUpITRepoFindByIdThrowsException();
        testAddStudentInvalid(OpCode.DB_Error);
    }

    @Test
    @Override
    void testCloseClassroomITRepoFindByIdThrowsException(){
        setUpITRepoFindByIdThrowsException();
        testCloseClassroomInvalid(OpCode.DB_Error);
    }

    @Test
    void testAddTeacherRepoFindByIdThrowsException(){
        setUpITRepoFindByIdThrowsException();
        testAddTeacherInvalid(OpCode.DB_Error);
    }

}
