package missions.room.ClassGroupTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.GroupData;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.GroupType;
import missions.room.Domain.Student;
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
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ClassGroupTestsAllStub {

    @InjectMocks
    protected ClassGroup classGroup;

    @Mock
    private Map<String, Student> students;

    @Mock
    private Student student;

    protected DataGenerator dataGenerator;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        dataGenerator=new DataGenerator();
        initMocks();
    }

    protected void initMocks() {
        closeable=MockitoAnnotations.openMocks(this);
        when(students.values())
                .thenReturn(Collections.singleton(student));
        when(students.isEmpty())
                .thenReturn(false);
        when(student.getStudentData())
                .thenReturn(dataGenerator
                        .getStudentData(Data.VALID));
        ReflectionTestUtils.setField(classGroup, // inject into this object
                "groupName", // assign to this field
                dataGenerator.getGroupData(Data.Valid_Group)
                        .getName());
        ReflectionTestUtils.setField(classGroup, // inject into this object
                "groupType", // assign to this field
                dataGenerator.getGroupData(Data.Valid_Group)
                        .getGroupType());
    }

    @Test
    public void getGroupDataHappyTestBothTeacher(){
        GroupData actual=classGroup.getGroupData(GroupType.BOTH);
        GroupData excepted=dataGenerator.getGroupData(Data.Valid_Group);
        assertEquals(actual, excepted);
    }

    @Test
    public void getGroupDataHappyTestValidGroup(){
        GroupData actual=classGroup.getGroupData(GroupType.B);
        GroupData excepted=dataGenerator.getGroupData(Data.Valid_Group);
        assertEquals(actual, excepted);
    }

    @Test
    public void getGroupDataInvalidGroupTest(){
        assertNull(classGroup.getGroupData(GroupType.A));
    }

    @Test
    public void getGroupDataInvalidGroupCTest(){
        ReflectionTestUtils.setField(classGroup, // inject into this object
                "groupType", // assign to this field
                GroupType.C);
        assertNull(classGroup.getGroupData(GroupType.BOTH));
    }

    @Test
    public void getGroupDataInvalidEmptyStudentsTest(){
        when(students.isEmpty())
                .thenReturn(true);
        assertNull(classGroup.getGroupData(GroupType.BOTH));
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
