/* Copyright Alan Gutierrez 2006 */
package com.goodworkalan.waste.unit;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.goodworkalan.waste.WasteException;

public class WasteExceptionTest
{
    @Test
    public void messageConstructor()
    {
        WasteException e = new WasteException(100, "Hello, World!", 10);
        assertEquals(e.getLocalizedMessage(), "Test with string \"Hello, World!\" and number 10.");
        assertEquals(e.getCode(), 100);
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */