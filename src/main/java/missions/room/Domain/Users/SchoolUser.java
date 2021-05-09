package missions.room.Domain.Users;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SchoolUser extends BaseUser {


    protected String firstName;

    protected String lastName;

/*
    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "id")
    @JoinColumn(name="dest",referencedColumnName = "alias")
    protected Map<String, Message> messages;*/

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
