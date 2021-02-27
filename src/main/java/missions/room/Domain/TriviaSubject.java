package missions.room.Domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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
