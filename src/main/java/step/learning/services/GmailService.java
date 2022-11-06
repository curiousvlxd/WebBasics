package step.learning.services;

import step.learning.entities.User;

import javax.inject.Singleton;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


@Singleton
public class GmailService implements EmailService
{
    //generate vrification code
    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
    
    @Override
    public boolean send(User user, String subject, String body) {
        boolean test = false;
        String toEmail = user.getEmail();
        String fromEmail = "javawebbasics@gmail.com";
        String password = "***************";
        try {
           // your host email smtp server details
           Properties pr = new Properties();
           pr.setProperty("mail.smtp.host", "smtp.gmail.com");
           pr.setProperty("mail.smtp.port", "587");
           pr.setProperty("mail.smtp.auth", "true");
           pr.setProperty("mail.smtp.starttls.enable", "true");
           pr.put("mail.smtp.socketFactory.port", "587");
           pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            //get session to authenticate the host email address and password
           Session session = Session.getInstance(pr, new Authenticator() {
               @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
               }
           });
           session.setDebug(true);
           //set email message details
          Message mess = new MimeMessage(session);

          //set from email address
           mess.setFrom(new InternetAddress(fromEmail));
          //set to email address or destination email address
           mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
           //set email subject
          mess.setSubject(subject);

          //set message text
            
            
//           mess.setText(String.format(
//                   "<h2>Hello</h2><p>To confirm your E-mail type a code <b>%s</b></p>" +
//                           "<p>Or follow this <a href='https://localhost:8443/WebBasics/verify/?userid=%s&verify=%s'>link</a></p>",
//                   user.getCode(), user.getId(), user.getCode() ) );

            mess.setContent(String.format(
                    "<h2>Hello</h2><p>To confirm your E-mail type a code <b>%s</b></p>" +
                            "<p>Or follow this <a href='https://localhost:8443/WebBasics/verify/?userid=%s&verify=%s'>link</a></p>",
                    user.getCode(), user.getId(), user.getCode() ), "text/html; charset=utf-8");
           //send the message
          Transport.send(mess);
           test=true;
       } catch (Exception e) {
          e.printStackTrace();
       }
        return test;
    }
}
