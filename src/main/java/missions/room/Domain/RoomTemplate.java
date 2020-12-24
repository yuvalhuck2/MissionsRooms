package missions.room.Domain;

import DataAPI.RoomTemplateDetailsData;
import DataAPI.RoomType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.mapping.Set;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class RoomTemplate {

    @Id
    private String roomTemplateId;

    private String name;

    private int  minimalMissionsToPass;

    @Enumerated(EnumType.ORDINAL)
    private RoomType type;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "missionId")
    @JoinColumn(name="roomTemplateId",referencedColumnName = "roomTemplateId")
    private Map<String,Mission> missions;

    public RoomTemplate() {
    }

    public RoomTemplate(RoomTemplateDetailsData details, HashMap<String, Mission> missions) {
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
        return missions.get(missionId);
    }
}
