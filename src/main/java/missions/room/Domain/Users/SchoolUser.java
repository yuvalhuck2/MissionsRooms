package missions.room.Domain.Users;

import missions.room.Domain.Message;

import javax.persistence.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SchoolUser extends User {


    protected String firstName;

    protected String lastName;

    public SchoolUser() {
        super();
    }

    public SchoolUser(String alias, String firstName, String lastName) {
        super(alias);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
