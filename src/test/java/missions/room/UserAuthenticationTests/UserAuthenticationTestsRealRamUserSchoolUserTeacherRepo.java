package missions.room.UserAuthenticationTests;

import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import DataAPI.GroupType;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserAuthenticationTestsRealRamUserSchoolUserTeacherRepo extends UserAuthenticationTestsRealRamUserSchoolUserRepo {

    @Autowired
    protected TeacherRepo realTeacherRepo;

    @Mock
    private TeacherCrudRepository mockTeacherCrudRepository;

    @Override
    protected void initTeacherRepo(Teacher teacher, String alias) {

        try {
            Field userRepo = TeacherManager.class.getDeclaredField("teacherRepo");

            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testRegisterInvalidExceptionTeacherRepositoryFindTeacherByStudent(){
        try {
            Field userRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,new TeacherRepo(mockTeacherCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        when(mockTeacherCrudRepository.findTeacherByStudent(anyString()))
                .thenThrow(new RuntimeException());
        checkWrongRegister(Data.VALID,OpCode.DB_Error);
    }

    @Test
    void testRegisterCodeInvalidExceptionTeacherRepositoryFind(){
        setUpRegisterCode();
        try {
            Field userRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,new TeacherRepo(mockTeacherCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        when(mockTeacherCrudRepository.findTeacherForRead(anyString()))
                .thenThrow(new RuntimeException());
        checkWrongRegisterCode(Data.VALID,Data.VALID,OpCode.DB_Error);
    }

    @Test
    @Override
    void testRegisterCodeInvalidNotExistInDbStudent(){
        setUpRegisterCode();
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_2Students_From_Different_Groups));
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.Valid_2Students_From_Different_Groups));
        String alias=dataGenerator.getRegisterDetails(Data.VALID).getAlias();
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,teacherAlias, GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.Not_Exist);
    }

    @Test
    @Override
    void testRegisterCodeInvalidAlreadyExistInDbStudent(){
        setUpRegisterCode();
        Student student = dataGenerator.getStudent(Data.VALID);
        String alias=student.getAlias();
        student.setPassword("pass");
        realSchoolUserRepo.save(student);
        Response<Boolean> response= userAuthenticationManager.registerCode(alias,code,teacherAlias, GroupType.A);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.Already_Exist);
    }
}
