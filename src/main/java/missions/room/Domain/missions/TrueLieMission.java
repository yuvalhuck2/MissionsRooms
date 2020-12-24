package missions.room.Domain.missions;

import java.util.Map;

import missions.room.Domain.Mission;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import DataAPI.RoomType;

@Entity
public class TrueLieMission extends Mission {
    
    private int answerTimeForStudent;
    private int fillTimeForStudent;

    @Transient
    private Map<String, TrueLie> truthAndLiesSentences;


    public TrueLieMission() {
    }

    public TrueLieMission(String missionId, Set<RoomType> missionTypes, Map<String, TrueLie>  truthAndLiesSentences, int answerTimeForStudent,int fillTimeForStudent) {
        this.truthAndLiesSentences = truthAndLiesSentences;
        this.answerTimeForStudent = answerTimeForStudent;
    }

    public int getAnswerTimeForStudent() {
        return answerTimeForStudent;
    }

    public void setAnswerTimeForStudent(int answerTimeForStudent) {
        this.answerTimeForStudent = answerTimeForStudent;
    }

    public int getFillTimeForStudent() {
        return fillTimeForStudent;
    }

    public void setFillTimeForStudent(int fillTimeForStudent) {
        this.fillTimeForStudent = fillTimeForStudent;
    }

    public Map<String, TrueLie> getTruthAndLiesSentences() {
        return truthAndLiesSentences;
    }

    public void setTruthAndLiesSentences(Map<String, TrueLie> truthAndLiesSentences) {
        this.truthAndLiesSentences = truthAndLiesSentences;
    }
}
