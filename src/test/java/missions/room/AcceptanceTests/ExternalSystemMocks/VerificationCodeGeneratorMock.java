package missions.room.AcceptanceTests.ExternalSystemMocks;

import ExternalSystems.VerificationCodeGenerator;

public class VerificationCodeGeneratorMock extends VerificationCodeGenerator {

    @Override
    public String getNext() {
        return "0";
    }
}
