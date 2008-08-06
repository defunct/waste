/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

final class Image
{
    public final String identifier;

    public final byte[] bytes;

    public final String contentType;

    public Image(String identifier, byte[] bytes, String contentType)
    {
        this.identifier = identifier;
        this.bytes = bytes;
        this.contentType = contentType;
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */