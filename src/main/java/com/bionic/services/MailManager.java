package com.bionic.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * package: com.bionic.services
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 01.12.2015
 */

@Service
public class MailManager {


    private static final Properties props = new Properties();
    private static String server = "smtp.gmail.com";
    private static String port = "587";
    private static String username = "university.bionic";
    private static String password = "test-platform";
    private static String fromAddr = "university.bionic@gmail.com";

    public MailManager(){
        props.put("mail.smtp.host",  server);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
    }

    public void send(String toAddr, String subject, String messageText) {
        Address address = null;
        try {
            address = new InternetAddress(toAddr);
        } catch (AddressException e) {
            System.out.println("Email address is invalid");
        }

        Session sess;
        sess = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message msg = new MimeMessage(sess);
        try {
            msg.setFrom(new InternetAddress(fromAddr));
            msg.setRecipient(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setText(messageText);
            Transport.send(msg);
        } catch (MessagingException e) {
           e.printStackTrace();
        }
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }
}
