/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class WasteException
extends RuntimeException
{
    private static final long serialVersionUID = 20080620L;

    private final int code;
    
    public WasteException()
    {
        super(null, null);
        code = 0;
    }
    
    public WasteException(Throwable cause)
    {
        super(null, cause);
        code = 0;
    }

    public WasteException(int code, Object... arguments)
    {
        super(message(code, arguments));
        this.code = code;
    }
    
    public WasteException(int code, Throwable cause, Object... arguments)
    {
        super(message(code, arguments), cause);
        this.code = code;
    }
    
    private static String message(Integer code, Object[] arguments)
    {
        ResourceBundle bundle = ResourceBundle.getBundle("com.agtrz.waste.exceptions");
        return MessageFormat.format(bundle.getString(code.toString()), arguments);
    }
    
    public int getCode()
    {
        return code;
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */