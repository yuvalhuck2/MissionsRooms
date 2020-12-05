package missions.room;

import Data.Data;
import Data.DataGenerator;
import Repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@SpringBootTest
class RoomApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    private LogicManager logicManager;
    private DataGenerator dataGenerator;

    @BeforeEach
    void setUp() {
        logicManager=new LogicManager();
        dataGenerator=new DataGenerator();
        studentRepository.save(dataGenerator.getStudent(Data.VALID));
    }

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    //    @Test
//    void testRegisterValid() {
//        Response<Boolean> response=logicManager.register(dataGenerator.getRegisterDetails(Data.VALID));
//        assertTrue(response.getValue());
//        assertEquals(response.getReason(), OpCode.Success);
//    }
//
//
//    @AfterEach
//    void tearDown(){
//        studentRepository.delete(dataGenerator.getStudent(Data.VALID));
//    }

}
