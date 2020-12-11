package Domain;

import javax.persistence.Entity;

@Entity
public class Supervisor extends Teacher {
    public Supervisor() {
    }

    public Supervisor(String alias, String firstName, String lastName) {
        super(alias, firstName, lastName,null,null);
    }
}
