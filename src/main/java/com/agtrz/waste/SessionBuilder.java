/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import java.util.Properties;

import javax.mail.Session;

public class SessionBuilder
{
    private String user;
    
    private String password;
    
    private Properties properties = new Properties();
    
    public String getUser()
    {
        return user;
    }
    
    public void setUser(String user)
    {
        this.user = user;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public Properties getProperties()
    {
        return properties;
    }
    
    public Session newSession()
    {
        return Session.getInstance(properties);
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */