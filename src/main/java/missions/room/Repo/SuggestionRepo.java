package missions.room.Repo;

import CrudRepositories.SuggestionCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
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
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Boolean> delete(String suggestionId) {
        try{
            suggestionCrudRepository.deleteById(suggestionId);
            return new Response<>(true,OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
