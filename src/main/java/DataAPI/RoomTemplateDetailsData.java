package DataAPI;

import java.util.List;

public class RoomTemplateDetailsData {
    String apiKey;
    private String id;
    private List<String> missions;
    private String name;
    private int minimalMissionsToPass;
    private RoomType type;

    public RoomTemplateDetailsData(List<String> missions, String name, int minimalMissionsToPass, RoomType type) {
        this.missions = missions;
        this.name = name;
        this.minimalMissionsToPass = minimalMissionsToPass;
        this.type = type;
    }

    public RoomTemplateDetailsData(String id, List<String> missions, String name, int minimalMissionsToPass, RoomType type) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setId(String id) {
        this.id = id;
    }
}
