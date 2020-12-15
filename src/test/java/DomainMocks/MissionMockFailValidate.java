package DomainMocks;

import DataAPI.OpCode;
import missions.room.Domain.Mission;

public class MissionMockFailValidate extends Mission {
    @Override
    public OpCode validate() {
        return OpCode.Null_Error;
    }
}
