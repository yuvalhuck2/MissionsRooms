package missions.room.CsvManagerTests;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.StudentCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.Classroom;
import missions.room.Domain.Users.IT;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.UploadCsvManager;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.ITRepo;
import missions.room.Repo.TeacherRepo;
import org.assertj.core.internal.Iterables;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Service
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class CsvManagerTestsRealRamRealRepos extends CsvManagerTestsRealRam {
    @Autowired
    protected ITRepo itRepo;

    @Autowired
    protected ClassroomRepo classroomRepo;

    @Autowired
    protected TeacherRepo teacherRepo;

    @Autowired
    private TeacherCrudRepository teacherCrudRepository;

    @Autowired
    private ClassroomRepository classRoomRepository;

    @Autowired
    private StudentCrudRepository studentCrudRepository;

    @Override
    protected void initClassroomRepo() {
        try {
            Field repo = UploadCsvManager.class.getDeclaredField("classRoomRepo");
            repo.setAccessible(true);
            repo.set(uploadCsvManager, classroomRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    protected void initITRepo(IT it, String itAlias, IT it2) {
        itRepo.save(it);
        try {
            Field repo = UploadCsvManager.class.getDeclaredField("itRepo");
            repo.setAccessible(true);
            repo.set(uploadCsvManager, itRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    protected void initTeacherRepo() {
        try {
            Field repo = UploadCsvManager.class.getDeclaredField("teacherRepo");
            repo.setAccessible(true);
            repo.set(uploadCsvManager, teacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    @Override
    @Test
    void uploadCSVSuccess(){
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(true, response.getValue());
        assertEquals(OpCode.Success, response.getReason());
        Iterable<Teacher> teachers = teacherCrudRepository.findAll();
        Iterable<Classroom> classrooms = classRoomRepository.findAll();
        assertEquals(2, getIterableSize(teachers));
        assertEquals(1, getIterableSize(classrooms));
        Iterator<Classroom> it = classrooms.iterator();
        Classroom classroom = it.next();
        Set<ClassGroup> classGroupList = classroom.getClassGroups();
        assertEquals(3, classGroupList.size());
        Iterable<Student> students = studentCrudRepository.findAll();
        assertEquals(2, getIterableSize(students));
    }

    private int getIterableSize(Iterable<?> iterable) {
        int size = 0;
        for(Object value : iterable) {
            size++;
        }
        return size;
    }
}
