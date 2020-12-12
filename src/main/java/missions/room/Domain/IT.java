package missions.room.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IT {
    @Id
    protected String id;
    
    protected String firstName;
    protected String lastName;

    @Column(unique=true)
    protected String phoneNumber;


    public IT() {
    }


    public IT(String id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    
}
