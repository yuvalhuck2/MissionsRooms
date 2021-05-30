package missions.room.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TriviaSubject {

    @Id
    private String name;

    public TriviaSubject() {
    }

    public TriviaSubject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
