package missions.room.Domain;

import DataAPI.OpCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="notification")
public abstract class Notification<T> implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name="reason")
    private OpCode reason;

    @Column(name="subscribe")
    private String subscribe;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    public Notification(OpCode reason) {
        this.reason = reason;
    }

    public Notification() {
    }

    public abstract T getValue();

    public OpCode getReason() {
        return reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if(this.id==null)
            this.id = id;
    }

    public void setName(String username){
        this.subscribe=username;
    }
}
