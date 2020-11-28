package Domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Participant {

    @Id
    protected String alias;

    public Participant() {
    }

    public Participant(String alias) {
        this.alias = alias;
    }
}
