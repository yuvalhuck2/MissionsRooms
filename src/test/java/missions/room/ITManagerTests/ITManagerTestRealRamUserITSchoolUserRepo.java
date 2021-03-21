package missions.room.ITManagerTests;

import CrudRepositories.SchoolUserCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import missions.room.Domain.Users.IT;
import missions.room.Domain.Users.SchoolUser;
import missions.room.Managers.ITManager;
import missions.room.Repo.SchoolUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ITManagerTestRealRamUserITSchoolUserRepo extends ITMangerTestsRealRamUserITRepo{

    @Autowired
    protected SchoolUserRepo realSchoolUserRepo;

    @Mock
    private SchoolUserCrudRepository mockSchoolUserRepository;

    @Override
    protected void initSchoolUserRepo(SchoolUser schoolUser) {
        realSchoolUserRepo.save(dataGenerator.getStudent(Data.VALID));
        try {
            Field schoolUserRepo = ITManager.class.getDeclaredField("schoolUserRepo");
            schoolUserRepo.setAccessible(true);
            schoolUserRepo.set(itManager,realSchoolUserRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    private void setUpMockUserRepo(){
        try {
            Field schoolUserRepo = ITManager.class.getDeclaredField("schoolUserRepo");
            schoolUserRepo.setAccessible(true);
            schoolUserRepo.set(itManager,new SchoolUserRepo(mockSchoolUserRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void updateUserDetailsSchoolUserRepoFindForWriteThrowsException(){
        setUpMockUserRepo();
        when(mockSchoolUserRepository.findUserForWrite(anyString()))
                .thenThrow(new RuntimeException());
        updateUserDetailsInvalidTest(OpCode.DB_Error);
    }

    @Test
    void updateUserDetailsSchoolUserRepoSaveThrowsException(){
        setUpMockUserRepo();
        when(mockSchoolUserRepository.findUserForWrite(anyString()))
                .thenReturn(dataGenerator.getStudent(Data.VALID));
        when(mockSchoolUserRepository.save(any()))
                .thenThrow(new RuntimeException());
        updateUserDetailsInvalidTest(OpCode.DB_Error);
    }


    @Test
    @Override
    void updateUserDetailsHappyTest() {
        super.updateUserDetailsHappyTest();
        SchoolUser student = realSchoolUserRepo.findUserForRead(userProfileData.getAlias()).getValue();
        assertEquals(student.getFirstName(),userProfileData.getFirstName());
        assertEquals(student.getLastName(),userProfileData.getLastName());
    }

    @Test
    @Override
    void updateUserDetailsHappyTestNullFirstName() {
        super.updateUserDetailsHappyTestNullFirstName();
        SchoolUser student = realSchoolUserRepo.findUserForRead(userProfileData.getAlias()).getValue();
        assertEquals(student.getLastName(),userProfileData.getLastName());
        assertEquals(student.getFirstName(),dataGenerator.getStudent(Data.VALID).getFirstName());
    }

    @Test
    @Override
    void updateUserDetailsHappyTestNullLastName() {
        super.updateUserDetailsHappyTestNullLastName();
        SchoolUser student = realSchoolUserRepo.findUserForRead(userProfileData.getAlias()).getValue();
        assertEquals(student.getFirstName(),userProfileData.getFirstName());
        assertEquals(student.getLastName(),dataGenerator.getStudent(Data.VALID).getLastName());
    }
}
