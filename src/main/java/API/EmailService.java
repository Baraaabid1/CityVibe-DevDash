package API;

import javax.mail.MessagingException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
        private final String senderEmail;
        private final String senderPassword;

        public EmailService(String senderEmail, String senderPassword) {
            this.senderEmail = senderEmail;
            this.senderPassword = senderPassword;
        }

        public void sendEmail(String toAddress, String subject, String content) throws MessagingException {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, senderPassword);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
                message.setSubject(subject);
                message.setText(content);

                Transport.send(message);

                System.out.println("Email envoyé avec succès");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

