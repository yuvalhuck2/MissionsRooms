package missions.room.Domain.missions;

import java.util.Random;

public class TrueLie {

    private String[] sentences;
    private int numOfTrueVotes;
    private int numOfLieVotes;
    //number of the correct sentence
    private int correctSentence;


    public TrueLie(String trueSentence, String lieSentence,int correctSentence) {
        sentences=new String[2];
        sentences[correctSentence]=trueSentence;
        sentences[1-correctSentence]=lieSentence;
        this.numOfTrueVotes = 0;
        this.numOfTrueVotes = 0;
    }
}
