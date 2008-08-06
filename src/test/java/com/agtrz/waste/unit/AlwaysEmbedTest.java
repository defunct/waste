/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste.unit;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.agtrz.waste.AlwaysEmbed;

public class AlwaysEmbedTest
{
    @Test
    public void shouldEmbed()
    {
        assertTrue(new AlwaysEmbed().shouldEmbed(null));
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */