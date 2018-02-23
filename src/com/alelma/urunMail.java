/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alelma;

import java.util.Date;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Mehmet
 */
@ManagedBean
@SessionScoped
public class urunMail {

    static String msgText1 = "This is a message body.\nHere's line two.";
    static String msgText2 = "This is the text in the message attachment.";
 

    public void mail() {

        String to = "m.mehmetgulsoy@gmail.com";
        String from = "mehmet@liman.com.tr";
        String host = "mail.liman.com.tr";
        boolean debug = true;
        // create some properties and get the default Session
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.user", "mehmet@liman.com.tr");
        //props.put("mail.smtp.password", "3141");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mehmet@liman.com.tr", "3141");
            }
        });

        session.setDebug(debug);
        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("JavaMail APIs Multipart Test");
            msg.setSentDate(new Date());
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(msgText1);
            // create and fill the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            // Use setText(text, charset), to show it off !
            mbp2.setText(msgText2, "us-ascii");
            // create the Multipart and its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            // add the Multipart to the message
            msg.setContent(mp);
            // send the message
            Transport.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
        }
    }

    public void mail2() throws NamingException, AddressException, MessagingException {
        
    	
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        Session session = (Session) envCtx.lookup("mail/Session");
        
        String from = "mehmet@liman.com.tr";
         
        String to = "m.mehmetgulsoy@gmail.com";
        session.setDebug(true);
        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Alelema Test");
            msg.setSentDate(new Date());
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(msgText1);
            // create and fill the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            // Use setText(text, charset), to show it off !
            mbp2.setText(msgText2, "us-ascii");
            // create the Multipart and its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            // add the Multipart to the message
            msg.setContent(mp);
            // send the message
            Transport.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
        }

    }
    
   


}
