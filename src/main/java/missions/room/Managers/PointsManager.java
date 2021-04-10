package missions.room.Managers;

import DataAPI.*;
import javassist.bytecode.Opcode;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Classroom;
import missions.room.Domain.Ram;
import missions.room.Domain.RecordTable;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.StudentRepo;
import missions.room.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CommonsLog
public class PointsManager extends TeacherManager  {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    /**
     * req 3.1 - watch rank Table details
     * @param apiKey - authentication object
     * @return the record table
     */
    public Response<RecordTableData> watchTable (String apiKey){
        String userAlias = ram.getAlias(apiKey);
        Response<Boolean> APIResponse=userRepo.isExistsById(userAlias);
        if(APIResponse.getReason()!= OpCode.Success){
            log.warn(String.format("There was a problem from kind %s when trying to find user %s",
                    APIResponse.getReason().toString(),
                    userAlias));
            return new Response<>(null,APIResponse.getReason());
        }
        if(!APIResponse.getValue()){
            log.warn(String.format("The user %s is not exist",
                    userAlias));
            return new Response<>(null,OpCode.Not_Exist);
        }
        RecordTable recordTable = ram.getRecordTable();
        if(recordTable == null){
            recordTable = getRecordTable();
            ram.updateTable(recordTable);
        }

        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        RecordTableData recordTableData= getRecordTableByUser(teacherResponse, recordTable);
        return new Response<>(recordTableData,OpCode.Success);
    }

    private RecordTableData getRecordTableByUser(Response<Teacher> teacherResponse, RecordTable recordTable) {
        if(teacherResponse.getReason()==OpCode.Success ){
            Teacher teacher=teacherResponse.getValue();
            if(teacher.isSupervisor()){
                return recordTable.getSupervisorData();
            }

            if(teacher.getClassroom()!=null) {
                return recordTable.getData(teacher.getClassroom());
            }
        }
        return recordTable.getData();
    }

    private RecordTable getRecordTable() {
        Response<List<Classroom>> classrooms = classroomRepo.findAll();
        RecordTable recordTableData =new RecordTable();
        classrooms.getValue()
                .parallelStream()
                .forEach(classroom -> classroom.getClassroomPointsData(recordTableData));
        return recordTableData;
    }

    /**
     * req 4.13 - deduce points to a student
     * @param apiKey - authentication object
     * @param studentAlias - the identifier of the student need to deduce points to.
     * @param pointsToReduce - the amount of points to deduce the user
     * @return if the points were deducted successfully
     */
    @Transactional
    public Response<Boolean> reducePoints(String apiKey, String studentAlias, int pointsToReduce){
        if(pointsToReduce <= 0){
            return new Response<>(false,OpCode.Negative_Points);
        }
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!=OpCode.Success){
            return new Response<>(false,teacherResponse.getReason());
        }

        Teacher teacher = teacherResponse.getValue();
        if(!teacher.isSupervisor() && teacher.getStudent(studentAlias) == null){
            log.info(String.format("function: reducePoints teacher %s is not in charge on student",studentAlias));
            return new Response<>(false,OpCode.Dont_Have_Permission);
        }

        Response<Student> studentResponse =studentRepo.findStudentForWrite(studentAlias);
        if(studentResponse.getReason()!= OpCode.Success){
            return new Response<>(false,studentResponse.getReason());
        }

        Student student = studentResponse.getValue();
        if(student == null){
            log.error(String.format("student %s was deleted through deducing points",studentAlias));
            return new Response<>(false,OpCode.Not_Exist_Student);
        }
        student.deducePoints(pointsToReduce);
        studentResponse = studentRepo.save(student);

        return new Response<>(studentResponse.getReason() == OpCode.Success, studentResponse.getReason());
    }

}
