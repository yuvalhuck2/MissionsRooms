package missions.room.Domain.Users;

import DataAPI.OpCode;
import DataAPI.UserProfileData;

import javax.persistence.Entity;

@Entity
public class IT extends User {

    public IT() {
        super();
    }

    public IT(String alias, String password) {
        super(alias,password);
    }

    @Override
    public OpCode getOpcode() {
        return OpCode.IT;
    }

    public UserProfileData getProfileData(){
        return new UserProfileData(alias,OpCode.IT);
    };
}
