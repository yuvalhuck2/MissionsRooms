package ExternalSystemMocks;

import ExternalSystems.MailSender;

import java.util.HashMap;

public class MailSenderTrueMock extends MailSender {

    @Override
    public boolean send(String to, String code) {

        return true;
    }

}
