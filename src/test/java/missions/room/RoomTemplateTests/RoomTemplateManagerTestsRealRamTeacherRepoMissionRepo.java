package missions.room.RoomTemplateTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import missions.room.Managers.RoomTemplateManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomTemplateManagerTestsRealRamTeacherRepoMissionRepo extends RoomTemplateManagerTestsRealRamTeacherRepo {


    @Override
    void setUpMocks(){
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        roomTemplateManager =new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
    }

    @Test
    void testMissionInvalidWrongMission(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.WRONG_ID, OpCode.Not_Mission);
        tearDownAddRoomTemplate();
    }

    @Override
    protected void tearDownAddRoomTemplate() {
        super.tearDownAddRoomTemplate();
        missionCrudRepository.deleteAll();
    }
}
