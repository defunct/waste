/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class VerpMessage
{
    private final Properties properties;
    
    private final MimeMessage message;
    
    private final Session session;
    
    private final String user;
    
    private final String password;
    
    public VerpMessage(Properties properties, String user, String password, String verp)
    {
        this.user = user;
        this.password = password;
        this.properties = new Properties(properties);
        this.properties.put("mail.smtp.from", verp);
        this.session = Session.getInstance(this.properties);
        this.message = new MimeMessage(session);
    }
    
    public MimeMessage getMimeMessage()
    {
        return message;
    }
    
    public Session getSession()
    {
        return session;
    }
    
    public void send() throws MessagingException
    {
        getMimeMessage().saveChanges();
        session.setDebug(true);
        Transport tr = session.getTransport("smtp");
        String host = properties.getProperty("mail.smtp.host");
        tr.connect(host, user, password);
        try
        {
            tr.sendMessage(message, message.getAllRecipients());
        }
        catch (SendFailedException e)
        {
            e.printStackTrace();
        }
        tr.close();
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */