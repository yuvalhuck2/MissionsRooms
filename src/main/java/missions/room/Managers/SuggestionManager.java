package missions.room.Managers;

import DataAPI.OpCode;
import DataAPI.Response;
import ExternalSystems.UniqueStringGenerator;
import missions.room.Domain.Student;
import missions.room.Domain.Suggestion;
import missions.room.Repo.SuggestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import Utils.Utils;
import org.springframework.stereotype.Service;

@Service
public class SuggestionManager extends StudentTeacherManager {

    private static final String PREFIX="sug";

    @Autowired
    private SuggestionRepo suggestionRepo;

    public Response<Boolean> addSuggestion(String apiKey, String suggestionText) {
        if(!Utils.checkString(suggestionText)){
            return new Response<>(false,OpCode.Wrong_Suggestion);
        }
        Response<Student> studentResponse = checkStudent(apiKey);
        if(studentResponse.getReason()!= OpCode.Success){
            return new Response<>(false
                    ,studentResponse.getReason());
        }
        Suggestion suggestion=new Suggestion(UniqueStringGenerator
                .getTimeNameCode(PREFIX)
                ,suggestionText);
        OpCode saveAnswer=suggestionRepo.save(suggestion)
                .getReason();
        return new Response<>(saveAnswer==OpCode.Success
                ,saveAnswer);
    }
}
