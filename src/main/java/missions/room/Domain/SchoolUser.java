package missions.room.Domain;

import javax.persistence.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SchoolUser extends User {


    protected String firstName;

    protected String lastName;


    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "id")
    @JoinColumn(name="dest",referencedColumnName = "alias")
    protected Map<String,Message> messages;

    public SchoolUser() {
    }

    public SchoolUser(String alias, String firstName, String lastName) {
        super(alias);
        this.firstName = firstName;
        this.lastName = lastName;
        messages=new ConcurrentHashMap<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }
}
