package DomainMocks;

import DataAPI.OpCode;
import missions.room.Domain.missions.Mission;

public class MissionMock extends Mission {
    @Override
    public OpCode validate() {
        return OpCode.Success;
    }
}
