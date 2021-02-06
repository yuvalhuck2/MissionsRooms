package missions.room.Domain;

import DataAPI.OpCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    protected String alias;

    protected String password;

    public User(){

    }

    public User(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }

    public User(String alias){
        this.alias=alias;
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

    public abstract OpCode getOpcode();
}

