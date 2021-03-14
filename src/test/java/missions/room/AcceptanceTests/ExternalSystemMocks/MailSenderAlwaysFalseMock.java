package missions.room.AcceptanceTests.ExternalSystemMocks;

import ExternalSystems.MailSender;

public class MailSenderAlwaysFalseMock extends MailSender {

    public MailSenderAlwaysFalseMock() {
    }

    @Override
    public Boolean send(String to, String code) {
        return false;
    }
}
