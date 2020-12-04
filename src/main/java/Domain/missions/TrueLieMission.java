package Domain.missions;

import java.util.Map;

import Domain.Mission;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import DataAPI.RoomType;

@Entity
public class TrueLieMission extends Mission {
    
    private String answerTimeForStudent;

    @Transient
    private Map<String, TrueLie> truthAndLiesSentences;


    public TrueLieMission() {
    }

    public TrueLieMission(String missionId, Set<RoomType> missionTypes, Map<String, TrueLie>  truthAndLiesSentences, String answerTimeForStudent) {
        this.truthAndLiesSentences = truthAndLiesSentences;
        this.answerTimeForStudent = answerTimeForStudent;
    }
    

    
    
}
