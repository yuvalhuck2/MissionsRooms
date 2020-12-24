package DataAPI;

import java.util.List;

public class RoomTemplateForSearch {
    private String id;
    private List<MissionData> missions;
    private String name;
    private int minimalMissionsToPass;
    private RoomType type;

    public RoomTemplateForSearch(String id,List<MissionData> missions, String name, int minimalMissionsToPass, RoomType type) {
        this.id=id;
        this.missions = missions;
        this.name = name;
        this.minimalMissionsToPass = minimalMissionsToPass;
        this.type = type;
    }

    public List<MissionData> getMissions() {
        return missions;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
