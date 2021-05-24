package missions.room.ChatManagerTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.SchoolUserCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.ChatManager;
import missions.room.Managers.UserAuthenticationManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.SchoolUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class ChatManagerTestsRealRamSchoolUserRepo extends ChatManagerTestsRealRam {

    @Autowired
    protected SchoolUserRepo realSchoolUserRepo;

    @Autowired
    protected ClassroomRepository classroomRepository;

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Mock
    private SchoolUserCrudRepository mockSchoolUserCrudRepository;

    @Override
    protected void initSchoolUserRepo() {
        classroomRepository.save(teacher.getClassroom());
        teacherCrudRepository.save(teacher);
        try {
            Field userRepo = ChatManager.class.getDeclaredField("schoolUserRepo");
            userRepo.setAccessible(true);
            userRepo.set(chatManager,realSchoolUserRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    @Transactional
    void enterChatSchoolUserRepoThrowsException(){
        try {
            Field userRepo = ChatManager.class.getDeclaredField("schoolUserRepo");
            userRepo.setAccessible(true);
            userRepo.set(chatManager,new SchoolUserRepo(mockSchoolUserCrudRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        when(mockSchoolUserCrudRepository.findById(anyString()))
                .thenThrow(new RuntimeException());
        enterChatInvalid(OpCode.DB_Error);
    }

    @Override
    protected void tearDownMocks() {
        teacherCrudRepository.deleteAll();
        classroomRepository.deleteAll();
        super.tearDownMocks();
    }
}
