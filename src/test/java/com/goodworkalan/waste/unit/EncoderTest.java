/* Copyright Alan Gutierrez 2006 */
package com.goodworkalan.waste.unit;

import static junit.framework.Assert.assertEquals;

import org.testng.annotations.Test;

import com.goodworkalan.waste.Encoder;

public class EncoderTest
{
    @Test public void encoder()
    {
        Encoder encoder = new Encoder();
        assertEquals(encoder.unencode("This &amp; that"), "This & that");
        assertEquals(encoder.unencode("This &#38; that"), "This & that");
        assertEquals( encoder.unencode("This & that"), "This & that");
        encoder = new Encoder();
        assertEquals(encoder.encode(encoder.unencode("This &amp; that")), "This &amp; that");
        assertEquals(encoder.encode(encoder.unencode("This &#38; that")), "This &#38; that");
        encoder = new Encoder();
        assertEquals(encoder.encode(encoder.unencode("This & that")), "This &amp; that");
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */