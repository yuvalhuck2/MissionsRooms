package missions.room.RoomTemplateTests;

import CrudRepositories.RoomTemplateCrudRepository;
import Data.Data;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.APIObjects.RoomTemplateDetailsData;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.missions.KnownAnswerMission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.*;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class RoomTemplateTestsRealRamTeacherRepoMissionRepoTemplateManagerRepo extends RoomTemplateManagerTestsRealRamTeacherRepoMissionRepo {

    @Autowired
    protected RoomTemplateCrudRepository roomTemplateCrudRepository2;


    @Override
    void setUpMocks() {
    }

    @Override
    protected void testAddRoomTemplateValidTest() {
        super.testAddRoomTemplateValidTest();
        RoomTemplate roomTemplate=roomTemplateCrudRepository.findAll().iterator().next();
        RoomTemplateDetailsData detailsData=dataGenerator.getRoomTemplateData(Data.VALID);
        assertEquals(roomTemplate.getName(),detailsData.getName());
        assertEquals(roomTemplate.getMinimalMissionsToPass(),detailsData.getMinimalMissionsToPass());
        assertEquals(roomTemplate.getType(),detailsData.getType());
        KnownAnswerMission mission= (KnownAnswerMission) dataGenerator.getMission(Data.Valid_Deterministic);
        KnownAnswerMission DBMission= (KnownAnswerMission) roomTemplate.getMission(mission.getMissionId());
        assertNotNull(DBMission);
        assertEquals(DBMission.getRealAnswer(),mission.getRealAnswer());
        assertEquals(DBMission.getQuestion(),mission.getQuestion());
        assertEquals(DBMission.getMissionTypes(),mission.getMissionTypes());

    }

    @Override
    protected void checkWrongAddRoomTemplate(Data data, OpCode opCode) {
        super.checkWrongAddRoomTemplate(data, opCode);
        assertFalse(roomTemplateCrudRepository2.findAll().iterator().hasNext());

    }

    @Test
    @Override
    void testSearchRoomTemplateEmpty(){
        setupSearchRoomTemplate();

        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        //roomTemplateCrudRepository=new RoomTemplateMockRepositorySearch(dataGenerator,Data.EMPTY);
        //roomTemplateManager=new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        testSearchRoomTemplateEmptyTest();
        teardownSearchTemplate();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));

    }

    @Test
    @Override
    void testSearchRoomTemplateValid(){
        setupSearchRoomTemplate();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        //roomTemplateCrudRepository=new RoomTemplateMockRepositorySearch(dataGenerator,Data.VALID);
        //roomTemplateManager=new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        testSearchRoomTemplateValidTest();
        teardownSearchTemplate();
        roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.VALID));
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));

    }

    @Override
    protected void tearDownAddRoomTemplate() {
        super.tearDownAddRoomTemplate();
        roomTemplateCrudRepository2.deleteAll();
    }
}
