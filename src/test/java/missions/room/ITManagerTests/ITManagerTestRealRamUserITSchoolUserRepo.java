package missions.room.ITManagerTests;

import CrudRepositories.SchoolUserCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import DataAPI.TeacherData;
import missions.room.Domain.Users.SchoolUser;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.ITManager;
import missions.room.Repo.SchoolUserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ITManagerTestRealRamUserITSchoolUserRepo extends ITMangerTestsRealRamBaseUserITRepo {

    @Autowired
    protected SchoolUserRepo realSchoolUserRepo;

    @Mock
    private SchoolUserCrudRepository mockSchoolUserRepository;

    @Autowired
    private SchoolUserCrudRepository schoolUserRepository;

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

    @Test
    @Override
    void testAddTeacherSchoolRepoSaveThrowsException(){
        setUpMockUserRepo();
        when(mockSchoolUserRepository.save(any()))
                .thenThrow(new RuntimeException());
        testAddTeacherInvalid(OpCode.DB_Error);
    }

    @Test
    @Override
    void testAddTeacherHappyCase() {
        super.testAddTeacherHappyCase();
        TeacherData teacherData = dataGenerator.getTeacherData(Data.VALID);
        Teacher teacher = (Teacher) schoolUserRepository.findById(teacherData.getAlias()).get();
        assertEquals(teacher.getFirstName(),teacherData.getFirstName());
        assertEquals(teacher.getLastName(),teacherData.getLastName());
    }

    @Test
    @Override
    void testAddSupervisorHappyCase() {
        super.testAddSupervisorHappyCase();
        TeacherData teacherData = dataGenerator.getTeacherData(Data.Supervisor);
        Teacher supervisor = (Teacher) schoolUserRepository.findById(teacherData.getAlias()).get();
        assertEquals(supervisor.getFirstName(),teacherData.getFirstName());
        assertEquals(supervisor.getLastName(),teacherData.getLastName());
        assertTrue(supervisor.isSupervisor());
    }
}
