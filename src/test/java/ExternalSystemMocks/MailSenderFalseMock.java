package ExternalSystemMocks;

import ExternalSystems.MailSender;

public class MailSenderFalseMock extends MailSender {

    @Override
    public boolean send(String to, String code) {
        return false;
    }
}
