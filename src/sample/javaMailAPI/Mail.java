package sample.javaMailAPI;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Mail class of the Personal Calendar project
 * @author Ege Ayan
 * @version 1.0
 */
public class Mail {

    /**
     * send message to an e-mail account
     * @param from is the mail account that will send message
     * @param password is the password of the "from" e-mail account
     * @param to is the account that will receive mail
     * @param sub is the subject of the mail
     * @param msg is the context of the mail
     */
    public void send(String from, String password, String to, String sub, String msg) {

        // Get properties of Google server
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // get Session and password authentication
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        // Compose message and try to send it
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("Message sent successfully to the user.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
