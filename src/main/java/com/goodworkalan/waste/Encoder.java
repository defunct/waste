/* Copyright Alan Gutierrez 2006 */
package com.goodworkalan.waste;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Encoder
{    
    private final static Map<String, String> ENTITIES = newEntities(); 
    
    private final static String DECIMAL = "01234567899";
    
    private final static String HEX = "0123456789abcdefABCEDEF";
    
    private final Map<String, String> entities;
    
    private final Map<Character, String> unencoded;
    
    public Encoder()
    {
        this(ENTITIES);
    }

    public Encoder(Map<String, String> entities)
    {
        this.entities = entities;
        this.unencoded = newReversedEntities(entities);
    }
 
    private static Map<String, String> newEntities()
    {
        Properties properties = new Properties();
        try
        {
            properties.load(Encoder.class.getResourceAsStream("entities.properties"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Map<String, String> map = new HashMap<String, String>();
        for (Object key : properties.keySet())
        {
            map.put((String) key, properties.getProperty((String) key));
        }
        return map;
    }

    private static Map<Character, String> newReversedEntities(Map<String, String> entities)
    {
        Map<Character, String> reversed = new HashMap<Character, String>();
        for (Map.Entry<String, String> entry : entities.entrySet())
        {
            Character ch = numeric(entry.getValue(), 0);
            reversed.put(ch, "&" + entry.getKey() + ";");
        }
        return reversed;
    }

    private Character named(String string, int index)
    {
        if (index + 2 >= string.length())
        {
            return null;
        }
        int start = index + 1;
        int stop = index + 1;
        for (;;)
        {
            if (string.charAt(stop) == ';')
            {
                break;
            }
            stop++;
            if (stop == string.length())
            {
                return null;
            }
        }
        String numeric = entities.get(string.substring(start, stop));
        if (numeric == null)
        {
            return null;
        }
        return numeric(numeric, 0);
    }

    private static Character numeric(String string, int index)
    {
        if (index + 2 >= string.length())
        {
            return null;
        }
        index++;
        if (string.charAt(index) != '#')
        {
            return null;
        }
        index++;
        String chars = DECIMAL;
        if (string.charAt(index) == 'x')
        {
            chars = HEX;
            index++;
        }
        StringBuilder number = new StringBuilder();
        for (;;)
        {
            char ch = string.charAt(index);
            if (ch == ';')
            {
                break;
            }
            if (chars.indexOf(ch) == -1)
            {
                return null;
            }
            number.append(ch);
            index++;
            if (index == string.length())
            {
                return null;
            }
        }
        int code = chars.equals(DECIMAL) ? Integer.parseInt(number.toString())
                                         : Integer.parseInt(number.toString(), 16);
        return (char) code;
    }
    
    public String unencode(String string)
    {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < string.length())
        {
            char ch = string.charAt(index);
            if ('&' == ch)
            {
                Character value = numeric(string, index);
                if (value == null)
                {
                    value = named(string, index);
                }
                else
                {
                    unencoded.put(value, "&#" + (int) value + ";");
                }
                if (value == null)
                {
                    builder.append(ch);
                    index++;
                }
                else
                {
                    builder.append(value);
                    index = string.indexOf(';', index) + 1;
                }
            }
            else
            {
                builder.append(ch);
                index++;
            }
        }
        return builder.toString();
    }
    
    public String encode(String string)
    {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < string.length(); index++)
        {
            char ch = string.charAt(index);
            String entity = unencoded.get(ch);
            if (entity != null)
            {
                builder.append(entity);
            }
            else
            {
                builder.append(ch);
            }
        }
        return builder.toString();
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */