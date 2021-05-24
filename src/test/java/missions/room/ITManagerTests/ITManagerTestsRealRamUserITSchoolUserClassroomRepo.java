package missions.room.ITManagerTests;

import CrudRepositories.ClassroomRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import missions.room.Domain.Classroom;
import missions.room.Managers.ITManager;
import missions.room.Repo.ClassroomRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ITManagerTestsRealRamUserITSchoolUserClassroomRepo extends ITManagerTestRealRamUserITSchoolUserRepo {

    @Autowired
    protected ClassroomRepo realClassroomRepo;

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
    protected void initClassroomRepo(Classroom empty) {
        classroomRepository.save(empty);
        classroomRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        classroomRepository.save(dataGenerator.getTeacher(Data.Valid_Group_A).getClassroom());
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

    @Test
    void testCloseClassroomClassroomRepoFindForWriteClassroomThrowsException(){
        setUpClassroomRepoMock();
        when(mockClassroomRepository.findClassroomForWrite(anyString()))
                .thenThrow(new RuntimeException());
        testCloseClassroomInvalid(OpCode.DB_Error);
    }

    @Test
    @Override
    void testCloseClassroomHappyCase(){
        super.testCloseClassroomHappyCase();
        assertFalse(classroomRepository.existsById(classroomName));
    }

    @Test
    void testCloseClassroomClassroomRepoDeleteClassroomThrowsException(){
        setUpClassroomRepoMock();
        when(mockClassroomRepository.findClassroomForWrite(anyString()))
                .thenReturn(dataGenerator.getClassroom(Data.Empty_Students));
        Mockito.doThrow(new RuntimeException())
                .when(mockClassroomRepository).delete(any());
        testCloseClassroomInvalid(OpCode.DB_Error);
    }

    @Override
    protected void testCloseClassroomInvalid(OpCode opCode) {
        super.testCloseClassroomInvalid(opCode);
        assertTrue(classroomRepository.existsById(dataGenerator
                .getClassroom(Data.Empty_Students)
                .getClassName()));
    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
    }
}
