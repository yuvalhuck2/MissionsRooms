package missions.room.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;

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
}
