package ExternalSystemMocks;

import ExternalSystems.MailSender;

public class MailSenderFalseMock extends MailSender {

    @Override
    public Boolean send(String to, String code) {
        return false;
    }
}
