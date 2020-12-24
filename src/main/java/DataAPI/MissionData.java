package DataAPI;

import java.util.List;
import java.util.Set;


public class MissionData {

    private String missionId;
    private Set<RoomType> missionTypes;
    private String name;
    private List<String> question;
    private int timeForAns;


    public MissionData(String missionId,Set<RoomType> missionTypes){
        this.missionId=missionId;
        this.missionTypes=missionTypes;
        this.question=null;
        this.timeForAns=-1;

    }
    public MissionData(String missionId, Set<RoomType> missionTypes, String name, List<String> question, int timeForAns) {
        this.missionId = missionId;
        this.missionTypes = missionTypes;
        this.name = name;
        this.question = question;
        this.timeForAns = timeForAns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    public void setTimeForAns(int timeForAns) {
        this.timeForAns = timeForAns;
    }

    public String getMissionId() {
        return missionId;
    }

    public Set<RoomType> getMissionTypes() {
        return missionTypes;
    }

    public String getName() {
        return name;
    }

    public List<String> getQuestion() {
        return question;
    }

    public int getTimeForAns() {
        return timeForAns;
    }
}
