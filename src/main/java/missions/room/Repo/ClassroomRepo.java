package missions.room.Repo;

import CrudRepositories.ClassroomRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Classroom;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@CommonsLog
public class ClassroomRepo {

    @Autowired
    private final ClassroomRepository classRoomRepository;

    public ClassroomRepo(ClassroomRepository classRoomRepository) {
        this.classRoomRepository = classRoomRepository;
    }

    public Response<Boolean> saveAll(List<Classroom> classes) {
        try{
            classRoomRepository.saveAll(classes);
            return new Response<>(true, OpCode.Success);
        } catch (Exception e) {
            log.error("failed to save all classrooms",e);
            return new Response<>(false,OpCode.DB_Error);
        }
    }

    public Response<Classroom> save(Classroom classRoom){
        try {
            return new Response<>(classRoomRepository.save(classRoom), OpCode.Success);
        }
        catch (Exception e){
            log.error("failed to save classroom",e);
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Classroom> findClassroomByStudent(String student) {

        try {
            Classroom classroom=classRoomRepository.findClassroomByStudent(student);
            if(classroom==null){
                classroom= null;
            }
            return new Response<>(classroom, OpCode.Success);
        }
        catch (Exception e){
            log.error("failed to find classroom by student" + student,e);
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<List<Classroom>> findAll() {
        try {
            return new Response<>(Lists.newArrayList(classRoomRepository.findAll()),OpCode.Success);
        }
        catch (Exception e){
            log.error("failed to find all classrooms",e);
            return new Response<>(new ArrayList<>(),OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<Classroom> findForWrite(String classroom) {
        try {
            Classroom myClassroom = classRoomRepository.findClassroomForWrite(classroom);
            if(myClassroom == null){
                log.info("classroom " + classroom + " is not exist");
                return new Response<>(null,OpCode.Not_Exist_Classroom);
            }
            return new Response<>(myClassroom, OpCode.Success);
        }
        catch (Exception e){
            log.error("error to find classroom " + classroom,e);
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
