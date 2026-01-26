package net.engineeringdigest.journalApp.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {


    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String to,String subject,String body){
        try{
            SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            System.out.println(simpleMailMessage);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("while sending the emaiol",e);
        }
    }

}
