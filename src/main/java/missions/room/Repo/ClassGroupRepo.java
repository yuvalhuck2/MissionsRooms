package missions.room.Repo;

import CrudRepositories.GroupRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassGroupRepo {

    @Autowired
    private final GroupRepository groupRepository;

    public ClassGroupRepo(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Response<ClassGroup> save(ClassGroup classGroup){
        try {
            return new Response<>(groupRepository.save(classGroup), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
