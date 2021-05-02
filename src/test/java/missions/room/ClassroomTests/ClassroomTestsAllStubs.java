package missions.room.ClassroomTests;

import Data.Data;
import Data.DataGenerator;
import DataObjects.FlatDataObjects.ClassRoomData;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.Classroom;
import DataObjects.FlatDataObjects.GroupType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ClassroomTestsAllStubs {

    @InjectMocks
    protected Classroom classroom;

    @Mock
    private ClassGroup classGroup;

    @Mock
    private Set<ClassGroup> classGroups;

    protected DataGenerator dataGenerator;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        initMocks();
    }

    protected void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        when(classGroups.parallelStream())
                .thenReturn(Collections.singleton(classGroup).parallelStream());
        when(classGroup.getGroupData(any()))
                .thenReturn(dataGenerator.getGroupData(Data.Valid_Group));
        ReflectionTestUtils.setField(classroom, // inject into this object
                "className", // assign to this field
                dataGenerator.getClassroom(Data.Valid_Classroom)
                        .getClassName());
    }

    @Test
    void getClassroomDataHappyTest(){
        ClassRoomData expected=dataGenerator.getClassroomData(Data.Valid_Classroom);
        ClassRoomData actual=classroom.getClassroomData(GroupType.BOTH);
        expected.getGroups().get(0).getStudents().get(0).setClassroom(dataGenerator.getClassroom(Data.Valid_Classroom));
        assertEquals(expected,actual);
    }

    @AfterEach
    void tearDown() {
        try {
            closeable.close();
        } catch (Exception e) {
            fail("close mocks when don't need to");
        }
    }
}
