package missions.room.Domain;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Subject {

    @Id
    private String name;

    @OneToMany
    @MapKeyColumn(name = "id")
    @JoinColumn(name="subject",referencedColumnName="name")
    private Map<String,TriviaQuestion> questions;


    public Subject() {
    }

    public Subject(String name, Map<String, TriviaQuestion> questions) {
        this.name = name;
        this.questions = questions;
    }
}
