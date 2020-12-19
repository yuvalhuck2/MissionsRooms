package missions.room.Domain;

import DataAPI.OpCode;
import DataAPI.RoomType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Mission implements Serializable {

    @Id
    protected String missionId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    protected Set<RoomType> missionTypes;

    public Mission() {
    }

    public Mission(String missionId, Set<RoomType> missionTypes) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
    }

    public OpCode validate(){
        if(missionTypes==null|| missionTypes.isEmpty()||missionTypes.contains(null)){
            return OpCode.Wrong_Type;
        }
        return OpCode.Success;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getMissionId() {
        return missionId;
    }

    public Set<RoomType> getMissionTypes() {
        return missionTypes;
    }

    public boolean containType(RoomType roomType){
        return missionTypes.contains(roomType);
    }
}
