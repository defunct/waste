/* Copyright Alan Gutierrez 2006 */
package com.goodworkalan.waste;

import java.util.Properties;

import javax.mail.Session;

public class SessionBuilder {
    private String user;

    private String password;

    private String transport = "smtp";

    private Properties properties = new Properties();

    public String getTransport() {
        return transport;
    }

    public void setTransport(String protocol) {
        this.transport = protocol;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getProperties() {
        return properties;
    }

    public Session newSession() {
        return Session.getInstance(properties);
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */