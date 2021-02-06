package missions.room.SuggestionManagerTests;

import missions.room.Managers.TeacherManager;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

public class SuggestionManagerTestsRealRamStudentTeacher extends SuggestionManagerTestsRealRamStudentRepo {

    @Autowired
    private TeacherRepo realTeacherRepo;

    protected void initMocks(){
        super.initMocks();
        try {
            Field teacherRepo = TeacherManager.class.getDeclaredField("teacherRepo");
            teacherRepo.setAccessible(true);
            teacherRepo.set(suggestionManager,realTeacherRepo);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }
}
