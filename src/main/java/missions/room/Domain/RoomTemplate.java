package missions.room.Domain;

import DataObjects.APIObjects.RoomTemplateDetailsData;
import DataObjects.APIObjects.RoomTemplateForSearch;
import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.RoomType;
import missions.room.Domain.missions.Mission;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class RoomTemplate {

    @Id
    private String roomTemplateId;

    private String name;

    private int minimalMissionsToPass;

    @Enumerated(EnumType.ORDINAL)
    private RoomType type;


    @ManyToMany(cascade =CascadeType.ALL,fetch = FetchType.EAGER)
    @OrderColumn(name="INDEX")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="missionTemplates",
            joinColumns ={@JoinColumn(name = "roomTemplateId")},
            inverseJoinColumns={@JoinColumn(name="missionId")}
    )
    private List<Mission> missions;

    public RoomTemplate() {

    }

    public RoomTemplate(RoomTemplateDetailsData details, List<Mission> missions) {
        this.name=details.getName();
        this.type=details.getType();
        this.roomTemplateId=details.getId();
        this.minimalMissionsToPass=details.getMinimalMissionsToPass();
        this.missions=missions;
    }

    public String getRoomTemplateId() {
        return roomTemplateId;
    }

    public String getName() {
        return name;
    }

    public int getMinimalMissionsToPass() {
        return minimalMissionsToPass;
    }

    public RoomType getType() {
        return type;
    }

    public Mission getMission(String missionId) {
        for(Mission m: missions){
            if(missionId.equals(m.getMissionId()))
                return m;
        }
        return null;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public Mission getMission(int missionIndex){
        if(this.missions.size()<=missionIndex){
            return null;
        }
        else
            return this.missions.get(missionIndex);
    }

    public RoomTemplateForSearch getRoomTemplateForSearch() {
        List<MissionData> missionList=missions.parallelStream()
                .map((Mission::getData))
                .collect(Collectors.toList());
        return new RoomTemplateForSearch(roomTemplateId,missionList,name,minimalMissionsToPass,type);
    }
}
