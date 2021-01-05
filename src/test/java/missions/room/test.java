package missions.room;

import CrudRepositories.*;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//@SpringBootTest
//public class test {
//
//    DataGenerator dataGenerator;
//
//    @Autowired
//    TeacherCrudRepository crudRepository;
//
//    @Autowired
//    ClassroomRepository classroomCrudRepository;
//
//    @Test
//    @Transactional
//    public void test(){
//        dataGenerator=new DataGenerator();
//        classroomCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
//        Classroom d=classroomCrudRepository.findAll().iterator().next();
//        crudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
//        Teacher t=crudRepository.findTeacherForRead(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getAlias());
//        System.out.println();
//    }
//
//    @Test
//    public void test1(){
//        Supervisor sima=new Supervisor("b","a","a",null, GroupType.BOTH);
//        crudRepository.save(sima);
//    }
//
//}

@SpringBootTest
public class test {

    DataGenerator dataGenerator;

    @Autowired
    TeacherCrudRepository crudRepository;

    @Autowired
    ClassroomRepository classroomCrudRepository;

    @Autowired
    MissionCrudRepository missionCrudRepository;

    @Autowired
    RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Autowired
    RoomCrudRepository roomCrudRepository;

    @Test
    @Transactional
    public void test() {
        dataGenerator = new DataGenerator();
        classroomCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        Classroom d = classroomCrudRepository.findAll().iterator().next();
        crudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        Teacher t = crudRepository.findTeacherForRead(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getAlias());
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        Mission mission = missionCrudRepository.findById(dataGenerator.getMission(Data.Valid_Deterministic).getMissionId()).get();
        RoomTemplate roomTemplate = dataGenerator.getRoomTemplate(Data.Valid_Classroom);
        roomTemplateCrudRepository.save(roomTemplate);
        roomTemplate = roomTemplateCrudRepository.findById(dataGenerator.getRoomTemplate(Data.Valid_Classroom).getRoomTemplateId()).get();
        roomCrudRepository.save(dataGenerator.getRoom(Data.Valid_Classroom));
        Room room = roomCrudRepository.findClassroomRoomForWriteByAlias(dataGenerator.getClassroom(Data.Valid_Classroom).getClassName());
        System.out.println();
    }
}




