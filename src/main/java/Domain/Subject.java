package Domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import java.util.Map;

@Entity
public class Subject {

    @Id
    private String name;

    @OneToMany
    @MapKey(name = "id")
    private Map<String,TriviaQuestion> questions;


    public Subject() {
    }

    public Subject(String name, Map<String, TriviaQuestion> questions) {
        this.name = name;
        this.questions = questions;
    }
}
