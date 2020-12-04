package Domain;

import DataAPI.RoomType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Mission {

    @Id
    protected String missionId;

    @ElementCollection(fetch = FetchType.EAGER)
    //@CollectionTable(name="missionType",joinColumns = {@JoinColumn(name="missionId")})
    @Enumerated(EnumType.ORDINAL)
    protected Set<RoomType> missionTypes;

    public Mission() {
    }

    public Mission(String missionId, Set<RoomType> missionTypes) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
    }
}
