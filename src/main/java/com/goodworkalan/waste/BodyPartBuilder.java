/* Copyright Alan Gutierrez 2006 */
package com.goodworkalan.waste;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimePart;

public interface BodyPartBuilder
{
    public void newBodyPart(MimePart part, Object model) throws IOException, MessagingException;
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */