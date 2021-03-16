package missions.room.Domain;

import DataAPI.StudentData;
import DataAPI.SuggestionData;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Suggestion {

    @Id
    private String id;

    private String suggestion;

    public Suggestion(String id, String suggestion) {
        this.id = id;
        this.suggestion = suggestion;
    }

    public Suggestion() {
    }

    public String getId() {
        return id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suggestion suggestion = (Suggestion) o;
        return  Objects.equals(suggestion.suggestion, this.suggestion);
    }

    public SuggestionData getData() {
        return new SuggestionData(id,suggestion);
    }
}
