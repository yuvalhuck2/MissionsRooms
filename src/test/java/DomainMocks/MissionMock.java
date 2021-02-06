package DomainMocks;

import DataAPI.OpCode;
import missions.room.Domain.Mission;

public class MissionMock extends Mission {
    @Override
    public OpCode validate() {
        return OpCode.Success;
    }
}
