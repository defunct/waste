/* Copyright Alan Gutierrez 2006 */
package com.goodworkalan.waste;

import java.io.IOException;
import java.io.StringWriter;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Generate message text using Freemarker.
 * 
 * @author Alan Gutierrez
 */
public class FreemarkerGenerator implements TextGenerator {
    /** The Freemarker template. */
    private final Template template;

    /**
     * Create a Freemarker generator with the given Freemarker template.
     * 
     * @param template
     *            The Freemarker template.
     */
    public FreemarkerGenerator(Template template) {
        this.template = template;
    }

    /**
     * Generate message text using the given data model.
     * 
     * @param model
     *            The data model.
     * @return The generated message text.
     */
    public String generate(Object model) {
        StringWriter writer = new StringWriter();
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            throw new WasteException(e);
        } catch (IOException e) {
            throw new WasteException(e);
        }
        return writer.toString();
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */