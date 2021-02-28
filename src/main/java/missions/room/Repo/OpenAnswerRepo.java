package missions.room.Repo;

import CrudRepositories.OpenAnswerRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.OpenAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAnswerRepo {

    @Autowired
    private OpenAnswerRepository openAnswerRepository;

    public Response<Boolean> saveOpenAnswer(OpenAnswer openAnswer){
        try{
            openAnswerRepository.save(openAnswer);
            return new Response<>(true, OpCode.Success);
        } catch(Exception e) {
            return new Response<>(false, OpCode.DB_Error);
        }
    }
}
