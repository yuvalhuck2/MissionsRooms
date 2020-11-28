package DataAPI;

import java.util.List;

public class RoomTemplateDetailsData {
    List<String> missions;
    String name;
    int minimalMissionsToPass;
    RoomType type;

    public RoomTemplateDetailsData(List<String> missions, String name, int minimalMissionsToPass, RoomType type) {
        this.missions = missions;
        this.name = name;
        this.minimalMissionsToPass = minimalMissionsToPass;
        this.type = type;
    }

    public List<String> getMissions() {
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
}
