package missions.room.Domain.missions;

import DataAPI.RoomType;
import missions.room.Domain.Mission;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

@Entity
public class StoryMission extends Mission {

    private int secondsForEachStudent;

    private int amountOfSentencesForEachStudent;

    @Transient
    private String story;

    public StoryMission() {
    }

    public StoryMission(String missionId, Set<RoomType> missionTypes, int secondsForEachStudent, int amountOfSentencesForEachStudent, String story) {
        super(missionId, missionTypes);
        this.secondsForEachStudent = secondsForEachStudent;
        this.amountOfSentencesForEachStudent = amountOfSentencesForEachStudent;
        this.story = story;
    }
}
