package missions.room.AcceptanceTests.AcceptanceTestDataObjects;


import java.util.List;

public class RoomTemplateDetailsTestData {

    List<String> missionIds;
    String name;
    int minimalMissionsToPass;
    ParticipantTypeTest type;

    public RoomTemplateDetailsTestData(List<String> missionIds, String name, int minimalMissionsToPass, ParticipantTypeTest type) {
        this.missionIds = missionIds;
        this.name = name;
        this.minimalMissionsToPass = minimalMissionsToPass;
        this.type = type;
    }

    public List<String> getMissionIds() {
        return missionIds;
    }

    public String getName() {
        return name;
    }

    public int getMinimalMissionsToPass() {
        return minimalMissionsToPass;
    }

    public ParticipantTypeTest getType() {
        return type;
    }
}
