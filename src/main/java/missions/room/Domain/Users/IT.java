package missions.room.Domain.Users;

import DataAPI.OpCode;

import javax.persistence.Entity;

@Entity
public class IT extends User {

    public IT() {
    }

    public IT(String alias, String password) {
        super(alias,password);
    }

    @Override
    public OpCode getOpcode() {
        return OpCode.IT;
    }

}
