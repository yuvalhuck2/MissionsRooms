package Domain;

import javax.persistence.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User  {

    @Id
    protected String alias;

    protected String firstName;

    protected String lastName;

    protected String password;

    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "id")
    @JoinColumn(name="dest",referencedColumnName = "alias")
    protected Map<String,Message> messages;

    public User() {
    }

    public User(String alias, String firstName, String lastName) {
        this.alias = alias;
        this.firstName = firstName;
        this.lastName = lastName;
        messages=new ConcurrentHashMap<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }
}
