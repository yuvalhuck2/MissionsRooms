package missions.room.UserAuthenticationTests;

import CrudRepositories.ClassroomRepository;
import Data.Data;
import DataAPI.OpCode;
import missions.room.Managers.UserAuthenticationManager;
import missions.room.Repo.ClassroomRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserAuthenticationAllReal extends UserAuthenticationTestsRealRamUserSchoolUserTeacherRepo{

    @Mock
    private ClassroomRepository mockClassroomRepository;

    @Override
    protected void initClassroomRepo() {
        try {
            Field userRepo = UserAuthenticationManager.class.getDeclaredField("classroomRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,realClassroomRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testRegisterCodeInvalidExceptionClassroomRepositorySave(){
        setUpRegisterCode();
        try {
            Field userRepo = UserAuthenticationManager.class.getDeclaredField("classroomRepo");
            userRepo.setAccessible(true);
            userRepo.set(userAuthenticationManager,new ClassroomRepo(mockClassroomRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        when(mockClassroomRepository.save(any()))
                .thenThrow(new RuntimeException());
        checkWrongRegisterCode(Data.VALID,Data.VALID,OpCode.DB_Error);
    }
}
