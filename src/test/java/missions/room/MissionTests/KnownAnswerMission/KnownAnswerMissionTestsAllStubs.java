package missions.room.MissionTests.KnownAnswerMission;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import missions.room.Domain.Mission;
import missions.room.Domain.missions.KnownAnswerMission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class KnownAnswerMissionTestsAllStubs {

    private DataGenerator dataGenerator;

    @BeforeEach
    void setUp(){
        dataGenerator=new DataGenerator();
    }

    @Test
    void testKnownAnswerMissionValid(){
        checkMissionValidate(Data.Valid_Deterministic, OpCode.Success);
    }

    @Test
    void testKnownAnswerMissionInvalidWrongType(){
        checkMissionValidate(Data.WRONG_TYPE_DETERMINSIC, OpCode.Wrong_Type);
    }

    @Test
    void testKnownAnswerMissionInvalidNullQuestion(){
        checkMissionValidate(Data.NULL_QUESTION_DETERMINISTIC, OpCode.Wrong_Question);
    }

    @Test
    void testKnownAnswerMissionInvalidEmptyQuestion(){
        checkMissionValidate(Data.EMPTY_QUESTION_DETERMINISTIC, OpCode.Wrong_Question);
    }

    @Test
    void testKnownAnswerMissionInvalidNullAnswer(){
        checkMissionValidate(Data.NULL_ANSWER_DETERMINISTIC, OpCode.Wrong_Answer);
    }

    @Test
    void testKnownAnswerMissionInvalidEmptyAnswer(){
        checkMissionValidate(Data.EMPTY_ANSWER_DETERMINISTIC, OpCode.Wrong_Answer);
    }

    private void checkMissionValidate(Data data, OpCode opCode) {
        Mission knownAnswerMission=dataGenerator.getMission(data);
        assertEquals(knownAnswerMission.validate(),opCode);
    }
}
