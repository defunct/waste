/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class VerpMessage
{
    private final MimeMessage message;

    private final SessionBuilder newSession;
    
    private final Session session;
    
    public VerpMessage(SessionBuilder newSession, String verp)
    {
        this.newSession = newSession;
        this.session = newSession.newSession();
        this.session.getProperties().put("mail.smtp.from", verp);
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
        String host = session.getProperty("smtp.mail.host");
        tr.connect(host, newSession.getUser(), newSession.getPassword());
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