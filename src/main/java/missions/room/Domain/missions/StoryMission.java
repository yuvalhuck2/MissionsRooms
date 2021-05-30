package missions.room.Domain.missions;

import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.RoomType;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

@Entity
public class StoryMission extends Mission{

    private static final String MISSION_NAME = "Story_Mission";

    @Transient
    private String story;

    public StoryMission() {
        story="";
    }

    public StoryMission(String missionId, Set<RoomType> missionTypes, String story) {
        super(missionId, missionTypes);
        this.story = story;
    }

    public String getStory() {
        return story;
    }

    @Override
    public String getMissionName() {
        return MISSION_NAME;
    }

    @Override
    protected MissionData completeTheRestOfMissionData(MissionData missionData) {
        missionData.setStory(story);
        return missionData;
    }

    public String updateStory(String sentence) {
        story += sentence+"\n";
        return story;
    }
}
