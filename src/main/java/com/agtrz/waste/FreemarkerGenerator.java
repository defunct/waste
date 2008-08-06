/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import java.io.IOException;
import java.io.StringWriter;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerGenerator
implements TextGenerator
{
    private final Template template;
    
    public FreemarkerGenerator(Template template)
    {
        this.template = template;
    }

    public String generate(Object model)
    {
        StringWriter writer = new StringWriter();
        try
        {
            template.process(model, writer);
        }
        catch (TemplateException e)
        {
            throw new WasteException(e);
        }
        catch (IOException e)
        {
            throw new WasteException(e);
        }
        return writer.toString();
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */