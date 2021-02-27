package missions.room.Domain.Users;

import DataAPI.OpCode;
import DataAPI.StudentData;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(user.alias, alias) &&
                Objects.equals(user.password,password);
    }
}

