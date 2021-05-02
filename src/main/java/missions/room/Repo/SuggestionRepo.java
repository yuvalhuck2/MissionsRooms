package missions.room.Repo;

import CrudRepositories.SuggestionCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@CommonsLog
public class SuggestionRepo {

    @Autowired
    private SuggestionCrudRepository suggestionCrudRepository;

    public SuggestionRepo(SuggestionCrudRepository mockSuggestionCrudRepository) {
        this.suggestionCrudRepository=mockSuggestionCrudRepository;
    }

    public Response<Suggestion> save(Suggestion suggestion){
        try {
            return new Response<>(suggestionCrudRepository.save(suggestion), OpCode.Success);
        }
        catch (Exception e){
            if(suggestion!=null){
                log.error(String.format("couldn't save suggestion with id %s",suggestion.getId()),e);
            }
            else{
                log.error("couldn't save suggestion with null suggestion",e);
            }
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<List<Suggestion>> findAllSuggestions() {
        try {
            List<Suggestion> suggestions= StreamSupport
                    .stream(suggestionCrudRepository.findAll()
                            .spliterator()
                            , false)
                    .collect(Collectors.toList());
            return new Response<>(suggestions,OpCode.Success);
        }
        catch (Exception e){
            log.error("couldn't find all suggestions",e);
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Boolean> delete(String suggestionId) {
        try{
            suggestionCrudRepository.deleteById(suggestionId);
            return new Response<>(true,OpCode.Success);
        }
        catch(Exception e){
            log.error(String.format("couldn't delete suggestion with id %s",suggestionId),e);
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
