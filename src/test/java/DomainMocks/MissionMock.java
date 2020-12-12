package DomainMocks;

import DataAPI.OpCode;
import Domain.Mission;

public class MissionMock extends Mission {
    @Override
    public OpCode validate() {
        return OpCode.Success;
    }
}
