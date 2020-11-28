package Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends Participant{

    protected String firstName;

    protected String lastName;

    @Column(unique=true)
    protected String phoneNumber;

    @OneToMany
    protected List<Message> messages;


    public Student() {
    }

    public Student(String alias, String firstName, String lastName, String phoneNumber) {
        super(alias);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        messages=new ArrayList<>();
    }
}
