package missions.room.addRoomTemplateTests;

import CrudRepositories.MissionCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import missions.room.Managers.AddRoomTemplateManager;
import missions.room.Repo.RoomTemplateRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
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
