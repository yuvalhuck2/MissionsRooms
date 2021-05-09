package missions.room.AcceptanceTests.AcceptanceTestDataObjects;

import java.util.List;
import java.util.Objects;

public class MissionDetailsTest {
    private String missionId;
    private List<ParticipantTypeTest> missionTypes;
    private String story;

    public MissionDetailsTest(String missionId, List<ParticipantTypeTest> missionTypes, String story) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
        this.story = story;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionDetailsTest that = (MissionDetailsTest) o;
        return missionId.equals(that.missionId) &&
                missionTypes.size() == (that.missionTypes.size()) &&
                story.equals(that.story);
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionId, story);
    }
}
