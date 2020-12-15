package missions.room.Domain;

import javax.persistence.*;
import java.util.Map;

@Entity
public class RoomTemplate {

    @Id
    private String roomTemplateId;


    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "missionId")
    @JoinColumn(name="roomTemplateId",referencedColumnName = "roomTemplateId")
    private Map<String,Mission> missions;


}
