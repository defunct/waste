/* Copyright Alan Gutierrez 2006 */
package com.goodworkalan.waste;

import javax.mail.MessagingException;
import javax.mail.internet.MimePart;

public class TextBodyPartBuilder
implements BodyPartBuilder
{
    private final TextGenerator generator;
    
    public TextBodyPartBuilder(TextGenerator generator)
    {
        this.generator = generator;
    }

    public void newBodyPart(MimePart mimePart, Object model) throws MessagingException
    {
        String text = generator.generate(model);
        mimePart.setText(text, "UTF-8");
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */