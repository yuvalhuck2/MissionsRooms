package Domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Participant {

    @Id
    protected String alias;

    public Participant() {
    }

    public Participant(String alias) {
        this.alias = alias;
    }
}
