package missions.room.Domain.missions;

import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.RoomType;
import missions.room.Domain.RoomTemplate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Mission implements Serializable {

    @Id
    protected String missionId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    protected Set<RoomType> missionTypes;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="missionTemplates",
            joinColumns ={@JoinColumn(name = "missionId")},
            inverseJoinColumns={@JoinColumn(name="roomTemplateId")}
    )
    private List<RoomTemplate> roomTemplateList;

    private int points;

    public Mission() {
    }

    public Mission(String missionId, Set<RoomType> missionTypes) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
        this.points=3;
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

    public int getPoints(){
        return this.points;
    }

    public MissionData getData() {
        MissionData missionData = new MissionData(missionId, missionTypes);
        missionData.setName(getMissionName());
        return completeTheRestOfMissionData(missionData);
    }

    //TODO need to become abstract after implement all the missions
    public String getMissionName() {
        return null;
    }

    //TODO need to become abstract after implement all the missions
    protected MissionData completeTheRestOfMissionData(MissionData missionData){
        return null;
    }
}
