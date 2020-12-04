package Domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Student extends Participant{

    protected String firstName;

    protected String lastName;

    @Column(unique=true)
    protected String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "id")
    @JoinColumn(name="dest",referencedColumnName = "alias")
    protected Map<String,Message> messages;


    public Student() {
    }

    public Student(String alias, String firstName, String lastName, String phoneNumber) {
        super(alias);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        messages=new ConcurrentHashMap<>();
    }
}
