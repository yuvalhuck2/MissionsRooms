package missions.room.ClassGroupTests;

import Data.Data;
import DataAPI.GroupType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;

public class ClassGroupTestsReal extends ClassGroupTestsAllStub{

    @Override
    protected void initMocks() {
        classGroup=this.dataGenerator.getClassGroup(Data.Valid_Group);
    }

    @Test
    @Override
    public void getGroupDataInvalidEmptyStudentsTest(){
        classGroup=dataGenerator.getClassGroup(Data.Empty_Students);
        assertNull(classGroup.getGroupData(GroupType.BOTH));
    }


    @Override
    @AfterEach
    void tearDown() {
    }
}
