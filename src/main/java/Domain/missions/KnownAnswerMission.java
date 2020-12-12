package Domain.missions;

import DataAPI.OpCode;
import DataAPI.RoomType;
import Domain.Mission;
import Utils.*;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class KnownAnswerMission extends Mission {

    private String question;

    private String realAnswer;

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
}
