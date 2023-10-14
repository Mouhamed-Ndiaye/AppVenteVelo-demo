package sn.edu.ugb.ipsl.appventevelo.mailsessionbean;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Stateless
public class EmailSessionBean {

    @Resource(lookup = "mail/AppVenteVeloMailSession")
    private Session session;

    final String username = "mouhamedn842@gmail.com";

    public void SendMail(String subject, String text) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(username));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("###### Message envoye avec succes!!! " + subject);

        } catch (MessagingException me) {
            System.out.println("###### Erreur d'envoi a cause de => " + me);
        }
    }
}

