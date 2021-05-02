package missions.room.Domain.missions;

import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.RoomType;

import javax.persistence.Entity;
import java.util.Collections;
import java.util.Set;

@Entity
public class OpenAnswerMission extends Mission {

    private String question;

    private final static String missionName="open_question_mission";


    public OpenAnswerMission(String missionId, Set<RoomType> missionType, String question) {
        super(missionId, missionType);
        this.question=question;
    }

    public OpenAnswerMission() {
    }

    @Override
    protected MissionData completeTheRestOfMissionData(MissionData missionData) {
        missionData.setQuestion(Collections.singletonList(question));
        return missionData;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String getMissionName() {
        return missionName;
    }
}
