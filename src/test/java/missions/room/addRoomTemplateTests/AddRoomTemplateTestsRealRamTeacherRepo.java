package missions.room.addRoomTemplateTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.AddRoomTemplateManager;
import missions.room.Repo.MissionRepo;
import missions.room.Repo.RoomTemplateRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AddRoomTemplateTestsRealRamTeacherRepo extends AddRoomTemplateTestsRealRam {

    @Override
    void setUpMocks(){
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        addRoomTemplateManager=new AddRoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
    }

    @Override
    void setUpAddRoomTemplate() {
        try {
            Field managerRam = AddRoomTemplateManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            ram=(Ram)managerRam.get(addRoomTemplateManager);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        super.setUpAddRoomTemplate();
    }

    @Test
    void testAddRoomTemplateInvalidWrongTeacher(){
        setUpAddRoomTemplate();
        //password for getting wrong alias
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.WRONG_NAME).getAlias());
        checkWrongAddRoomTemplate(Data.VALID,OpCode.Not_Exist);
        tearDownAddRoomTemplate();
    }

}
