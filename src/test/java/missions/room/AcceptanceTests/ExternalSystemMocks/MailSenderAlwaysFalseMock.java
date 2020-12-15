package missions.room.AcceptanceTests.ExternalSystemMocks;

import ExternalSystems.MailSender;

public class MailSenderAlwaysFalseMock extends MailSender {

    public MailSenderAlwaysFalseMock() {
    }

    @Override
    public boolean send(String to, String code) {
        return false;
    }
}
