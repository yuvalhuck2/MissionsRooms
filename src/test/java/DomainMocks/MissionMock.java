package DomainMocks;

import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.OpCode;
import missions.room.Domain.missions.Mission;

public class MissionMock extends Mission {
    @Override
    public OpCode validate() {
        return OpCode.Success;
    }

    @Override
    public String getMissionName() {
        return null;
    }

    @Override
    protected MissionData completeTheRestOfMissionData(MissionData missionData) {
        return null;
    }
}
