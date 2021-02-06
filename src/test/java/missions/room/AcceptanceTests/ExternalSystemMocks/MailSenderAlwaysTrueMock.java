package missions.room.AcceptanceTests.ExternalSystemMocks;

import ExternalSystems.MailSender;

public class MailSenderAlwaysTrueMock extends MailSender {

    public MailSenderAlwaysTrueMock() {
    }

    @Override
    public boolean send(String to, String code) {
        return true;
    }
}
