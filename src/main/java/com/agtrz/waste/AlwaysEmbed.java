/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import java.util.Map;

public class AlwaysEmbed
implements Embedder
{
    public boolean shouldEmbed(Map<String, String> attributes)
    {
        return true;
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */