package missions.room.UserAuthenticationTests;

import CrudRepositories.SchoolUserCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.APIObjects.RegisterDetailsData;
import DataObjects.FlatDataObjects.Response;
import DataObjects.APIObjects.TeacherData;
import missions.room.Domain.Users.SchoolUser;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.UserAuthenticationManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.SchoolUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserAuthenticationTestsRealRamUserSchoolUserRepo extends UserAuthenticationTestsRealRamBaseUserRepo {

    @Autowired
    protected SchoolUserRepo realSchoolUserRepo;

    @Autowired
    protected ClassroomRepo realClassroomRepo;

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Mock
    private SchoolUserCrudRepository mockSchoolUserCrudRepository;

    @Override
    protected void initSchoolUserRepo(Student student) {
        Teacher teacher=dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C);
        classroomRepository.save(teacher.getClassroom());
        teacherCrudRepository.save(teacher);
        try {
            Field userRepo = UserAuthenticationManager.class.getDeclaredField("schoolUserRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,realSchoolUserRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    @Transactional
    void testRegisterInvalidExceptionUserRepositoryFind(){
        try {
            Field userRepo = UserAuthenticationManager.class.getDeclaredField("schoolUserRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,new SchoolUserRepo(mockSchoolUserCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        when(mockSchoolUserCrudRepository.findById(anyString()))
                .thenThrow(new RuntimeException());
        checkWrongRegister(Data.VALID,OpCode.DB_Error);
    }

    @Test
    @Override
    void testRegisterInvalidAlreadyRegisteredStudent(){
        Student student = dataGenerator.getStudent(Data.VALID);
        student.setPassword("pass");
        Response<SchoolUser> res=realSchoolUserRepo.save(student);
        RegisterDetailsData detailsData=dataGenerator.getRegisterDetails(Data.VALID);
        Response<List<TeacherData>> response= userAuthenticationManager.register(detailsData);
        assertNull(response.getValue());
        assertEquals(response.getReason(), OpCode.Already_Exist);
    }

    @Override
    @AfterEach
    void tearDown() {
        teacherCrudRepository.deleteAll();
        classroomRepository.deleteAll();
        super.tearDown();
    }
}
