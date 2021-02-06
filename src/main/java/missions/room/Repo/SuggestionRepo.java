package missions.room.Repo;

import CrudRepositories.SuggestionCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Mission;
import missions.room.Domain.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
