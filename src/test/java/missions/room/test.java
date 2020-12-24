package missions.room;

import CrudRepositories.ClassroomRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Classroom;
import missions.room.Domain.GroupType;
import missions.room.Domain.Supervisor;
import missions.room.Domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class test {

    DataGenerator dataGenerator;

    @Autowired
    TeacherCrudRepository crudRepository;

    @Autowired
    ClassroomRepository classroomCrudRepository;

    @Test
    @Transactional
    public void test(){
        dataGenerator=new DataGenerator();
        classroomCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        Classroom d=classroomCrudRepository.findAll().iterator().next();
        crudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        Teacher t=crudRepository.findTeacherForRead(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getAlias());
        System.out.println();
    }

    @Test
    public void test1(){
        Supervisor sima=new Supervisor("b","a","a",null, GroupType.BOTH);
        crudRepository.save(sima);
    }

}
