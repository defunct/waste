/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import java.util.Properties;

public class VerpMessageBuilder
{
    private final Properties properties;
    
    private final String mailbox;
    
    private final String user;
    
    private final String password;
    
    public VerpMessageBuilder(Properties properties, String user, String password, String mailbox)
    {
        this.properties = properties;
        this.mailbox = mailbox;
        this.user = user;
        this.password = password;
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
        return new VerpMessage(properties, user, password, mailbox + "-" + id);
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */