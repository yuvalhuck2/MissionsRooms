package missions.room.ITManagerTests;

import CrudRepositories.ClassroomRepository;
import Data.Data;
import DataAPI.OpCode;
import missions.room.Managers.ITManager;
import missions.room.Repo.ClassroomRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ITManagerTestsRealRamUserITSchoolUserClassroomRepo extends ITManagerTestRealRamUserITSchoolUserRepo{

    @Autowired
    protected ClassroomRepo realClassroomRepo;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Mock
    private ClassroomRepository mockClassroomRepository;

    private void setUpClassroomRepoMock(){
        try {
            Field classroomRepo = ITManager.class.getDeclaredField("classroomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(itManager, new ClassroomRepo(mockClassroomRepository));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    protected void initClassroomRepo() {
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        try {
            Field classroomRepo = ITManager.class.getDeclaredField("classroomRepo");
            classroomRepo.setAccessible(true);
            classroomRepo.set(itManager, realClassroomRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Override
    void testAddStudentClassroomRepoFindForWriteClassroomThrowsException(){
        setUpClassroomRepoMock();
        when(mockClassroomRepository.findClassroomForWrite(anyString()))
                .thenThrow(new RuntimeException());
        testAddStudentInvalid(OpCode.DB_Error);
    }

    @Test
    @Override
    void testAddStudentClassroomRepoSaveThrowsException(){
        setUpClassroomRepoMock();
        when(mockClassroomRepository.findClassroomForWrite(anyString()))
                .thenReturn(dataGenerator.getClassroom(Data.Valid_Classroom));
        when(mockClassroomRepository.save(any()))
                .thenThrow(new RuntimeException());
        testAddStudentInvalid(OpCode.DB_Error);
    }

    @Override
    @AfterEach
    void tearDown() {
        classroomRepository.deleteAll();
        super.tearDown();
    }
}
