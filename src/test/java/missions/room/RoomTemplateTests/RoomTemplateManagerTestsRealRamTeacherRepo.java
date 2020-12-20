package missions.room.RoomTemplateTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.RoomTemplateManager;
import missions.room.Managers.TeacherManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomTemplateManagerTestsRealRamTeacherRepo extends RoomTemplateManagerTestsRealRam {

    @Override
    void setUpMocks(){
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        roomTemplateManager =new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
    }

    @Override
    void setUpAddRoomTemplate() {
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            ram=(Ram)managerRam.get(roomTemplateManager);

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
