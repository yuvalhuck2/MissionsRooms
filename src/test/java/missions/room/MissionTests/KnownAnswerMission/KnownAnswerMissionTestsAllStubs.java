package missions.room.MissionTests.KnownAnswerMission;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import missions.room.Domain.missions.Mission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

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
    void testKnownAnswerMissionInvalidNullTypes(){
        checkMissionValidate(Data.NULL_TYPES_DETERMINSIC, OpCode.Wrong_Type);
    }

    @Test
    void testKnownAnswerMissionInvalidEmptyTypes(){
        checkMissionValidate(Data.EMPTY_TYPE_DETERMINISTIC, OpCode.Wrong_Type);
    }

    @Test
    void testKnownAnswerMissionInvalidTypesWithNull(){
        checkMissionValidate(Data.TYPES_WITH_NULL_DETERMINISTIC, OpCode.Wrong_Type);
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
