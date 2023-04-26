package esprit.changemakers.sportshub.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public static void sendMail(String recipent,String emailSubject,String emailContent) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myEmail = "henihana1234@gmail.com";
        String myEmailPassword = "123456789aQ";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,myEmailPassword);
            }
        });
        Message message = prepareMessage(session,myEmail,recipent,emailSubject,emailContent);
        Transport.send(message);
        System.out.println("Message Send !");
    }

    private static Message prepareMessage(Session session, String myEmail, String recipent, String emailSubject, String emailContent) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipent));
            message.setSubject(emailSubject);
            message.setText(emailContent);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
