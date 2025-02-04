package ExternalSystems;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    private final String from="escaperoomapp2021@yahoo.com";
    private final String password="zebwxxoywhmxhvpy";
    private final String subject="Your escape room password";

    private Properties properties;


    public MailSender() {
        properties = new Properties();

        // Setup mail server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "587");
    }

    public Boolean send(String to, String code){

        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,password);
            }
        });

        Message message=prepareMessage(session,to,code);

        try {
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    private Message prepareMessage(Session session,String to,String code) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            message.setText(code+"\n");
            return message;
        }
        catch (Exception e){
            return null;
        }

    }
}
