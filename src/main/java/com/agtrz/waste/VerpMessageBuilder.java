/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;


public class VerpMessageBuilder
{
    private final SessionBuilder newSession;
    
    private final String mailbox;
    
    public VerpMessageBuilder(SessionBuilder newSession, String mailbox)
    {
        this.newSession = newSession;
        this.mailbox = mailbox;
    }
    
    public static String escape(String address)
    {
        if (address.indexOf('=') != -1)
        {
            throw new WasteException(105, address);
        }

        String verp = "bounces-" + address.replace('@', '=');
        if (verp.indexOf('@') != -1)
        {
            throw new WasteException(105, verp, address);
        }
        
        return verp;
    }
    
    public VerpMessage email(String address)
    {
        return id(escape(address));
    }
    
    public VerpMessage email(String address, String id)
    {
        return id(escape(address) + id);
    }
    
    public VerpMessage id(String id)
    {
        return new VerpMessage(newSession, mailbox + "-" + id);
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */