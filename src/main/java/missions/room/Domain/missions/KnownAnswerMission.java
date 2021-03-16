package missions.room.Domain.missions;

import DataAPI.MissionData;
import DataAPI.OpCode;
import DataAPI.RoomType;
import Utils.*;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class KnownAnswerMission extends Mission {

    private String question;

    private String realAnswer;

    private final static String missionName="Known answer mission";

    public KnownAnswerMission() {
    }

    @Override
    public OpCode validate() {
        if(!Utils.checkString(question)){
            return OpCode.Wrong_Question;
        }
        if(!Utils.checkString(realAnswer)){
            return OpCode.Wrong_Answer;
        }
        return super.validate();

    }

    public KnownAnswerMission(String missionId, Set<RoomType> missionTypes, String question, String realAnswer) {
        super(missionId, missionTypes);
        this.question = question;
        this.realAnswer = realAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getRealAnswer() {
        return realAnswer;
    }

    @Override
    protected MissionData completeTheRestOfMissionData(MissionData missionData) {
        List<String> questList = new ArrayList<>();
        List<String> answerList = new ArrayList<>();
        questList.add(question);
        missionData.setQuestion(questList);
        answerList.add(realAnswer);
        missionData.setAnswers(answerList);
        return missionData;
    }

    @Override
    public String getMissionName() {
        return missionName;
    }
}
