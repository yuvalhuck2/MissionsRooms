package missions.room.Domain.Users;

import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.UserProfileData;

import javax.persistence.Entity;

@Entity
public class IT extends BaseUser {

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
