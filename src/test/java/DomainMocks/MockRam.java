package DomainMocks;

import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Ram;

public class MockRam extends Ram {

    private final DataGenerator dataGenerator;

    public MockRam(DataGenerator dataGenerator) {
        this.dataGenerator=dataGenerator;
    }

    @Override
    public String getApi(String apiKey) {
        return dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias();
    }
}
