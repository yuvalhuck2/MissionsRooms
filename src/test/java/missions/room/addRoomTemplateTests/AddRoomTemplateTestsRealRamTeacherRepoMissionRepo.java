package missions.room.addRoomTemplateTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import missions.room.Managers.AddRoomTemplateManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class AddRoomTemplateTestsRealRamTeacherRepoMissionRepo extends AddRoomTemplateTestsRealRamTeacherRepo {


    @Override
    void setUpMocks(){
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        addRoomTemplateManager=new AddRoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
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
