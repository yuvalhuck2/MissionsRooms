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
    

    
    
}
