package missions.room.ClassroomTests;

import Data.Data;
import org.junit.jupiter.api.AfterEach;

public class ClassroomTestsReal extends ClassroomTestsAllStubs {

    @Override
    protected void initMocks() {
        classroom=this.dataGenerator.getClassroom(Data.Valid_Classroom);
    }

    @Override
    @AfterEach
    void tearDown() {
    }
}
