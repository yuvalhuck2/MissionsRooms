package AcceptanceTests.AcceptanceTestDataObjects;


import java.util.List;

public class RoomTemplateDetailsTestData {

    List<String> missionIds;
    String name;
    int minimalMissionsToPass;
    ParticipantTypeTest type;

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
