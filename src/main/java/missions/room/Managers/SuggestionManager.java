package missions.room.Managers;

import DataObjects.APIObjects.SuggestionData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Suggestion;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Repo.SuggestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class SuggestionManager extends StudentTeacherManager {

    private static final String PREFIX="sug";

    @Autowired
    private SuggestionRepo suggestionRepo;

    /**
     * req 3.7 - add suggestion
     * @param apiKey - authentication object
     * @param suggestionText - suggestion to a mission the student want to send
     * @return if the suggestion was added successfully
     */
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

    /**
     * req 4.11 - watch student's suggestions
     * @param apiKey - authentication object
     * @return the student's suggestions
     */
    public Response<List<SuggestionData>> watchSuggestions(String apiKey) {
        Response<Teacher> teacherResponse=checkTeacher(apiKey);
        if (teacherResponse.getReason()!=OpCode.Success){
            return new Response<>(null
                    ,teacherResponse.getReason());
        }
        Response<List<Suggestion>> suggestionsResponse=suggestionRepo.findAllSuggestions();
        if(suggestionsResponse.getReason()!=OpCode.Success){
            return new Response<>(null,suggestionsResponse.getReason());
        }
        return new Response<>(suggestionRepo.findAllSuggestions()
                .getValue()
                .stream()
                .map((Suggestion::getData))
                .collect(Collectors.toList()),
                OpCode.Success);
    }

    /**
     * req 4.12 - delete student's suggestion
     * @param apiKey - authentication object
     * @param suggestionId - identifier of the suggestion need to be deleted
     * @return if the suggestion was deleted successfully
     */
    public Response<Boolean> deleteSuggestion(String apiKey, String suggestionId) {
        Response<Teacher> teacherResponse=checkTeacher(apiKey);
        if (teacherResponse.getReason()!=OpCode.Success){
            return new Response<>(null
                    ,teacherResponse.getReason());
        }
        return suggestionRepo.delete(suggestionId);
    }
}
