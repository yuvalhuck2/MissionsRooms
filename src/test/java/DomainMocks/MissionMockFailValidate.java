package DomainMocks;

import DataObjects.FlatDataObjects.OpCode;
import missions.room.Domain.missions.Mission;

public class MissionMockFailValidate extends Mission {
    @Override
    public OpCode validate() {
        return OpCode.Null_Error;
    }
}
