package missions.room.RoomManagerMockitoTests;


import CrudRepositories.ClassroomRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.TeacherManager;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomManagerTestsRealRamTeacherRepo extends RoomManagerTestsRealRam {

    @Autowired
    private TeacherRepo teacherRepoReal;

    @Autowired
    private TeacherCrudRepository teacherCrudRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    protected void initTeacherRepo( Teacher teacher) {


        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_Without_Students));
        teacherCrudRepository.save(teacher);
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITHOUT_CLASSROOM));
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM2));

        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(roomManager,teacherRepoReal);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }





    }



    @Override
    @AfterEach
    void tearDown() {

        teacherCrudRepository.deleteAll();
        classroomRepository.deleteAll();

        super.tearDown();
    }
}
