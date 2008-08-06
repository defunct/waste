/* Copyright Alan Gutierrez 2006 */
package com.agtrz.waste;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.util.ByteArrayDataSource;

public class MarkupBodyPartBuilder
implements BodyPartBuilder
{
    
    private final static Pattern IMG = Pattern.compile("<img([^>]+)>", Pattern.CASE_INSENSITIVE);
    
    private final static Pattern A = Pattern.compile("<a([^>]+)>", Pattern.CASE_INSENSITIVE);
    
    private final static Pattern CONTENTS = Pattern.compile("\\s*([^=]+)=\"([^\"]*)\"");

    private final TextGenerator generator;
    
    private final BodyPartBuilder alternate;
    
    private final Linker linker;
    
    private final Embedder embedder;
    
    private final Map<String, Image> mapOfImages;
    
    public MarkupBodyPartBuilder(TextGenerator generator, BodyPartBuilder alternate, Linker linker, Embedder embedder)
    {
        this.generator = generator;
        this.alternate = alternate;
        this.linker = linker;
        this.embedder = embedder;
        this.mapOfImages = new HashMap<String, Image>();

    }

    public void newBodyPart(MimePart part, Object model) throws IOException, MessagingException
    {
        MimeBodyPart text = new MimeBodyPart();
        alternate.newBodyPart(text, model);

        List<Image> listOfImages = new ArrayList<Image>();
        Encoder encoder = new Encoder();
        String markup = images(
            links(generator.generate(model), encoder, linker, model),
            encoder, embedder, listOfImages);

        BodyPart html = new MimeBodyPart();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Writer encoded = new OutputStreamWriter(os, "UTF-8");
        encoded.write(markup);
        encoded.close();

        DataHandler hander = new DataHandler(new ByteArrayDataSource(os.toByteArray(), "text/html"));
        html.setDataHandler(hander);
        
        html.setHeader("Content-Type", "text/html; charset=\"UTF-8\"");
//        html.setHeader("Content-Type", "text/html; charset=\"iso-8859-1\"");
//        html.setHeader("Content-Transfer-Encoding", "7bit");
        
        MimeMultipart multipart = new MimeMultipart("alternative");

        multipart.addBodyPart(text);

        for (Image image : listOfImages)
        {
            BodyPart img = new MimeBodyPart();
            ByteArrayDataSource fds = new ByteArrayDataSource(image.bytes, image.contentType);
            img.setDataHandler(new DataHandler(fds));
            img.setHeader("Content-ID", "<" + image.identifier + ">");
            multipart.addBodyPart(img);
        }

        multipart.addBodyPart(html);
        
        part.setContent(multipart);
    }

    private String images(CharSequence message, Encoder encoder, Embedder embeder, List<Image> listOfImages)
    {
        int last = 0;
        StringBuilder builder = new StringBuilder();
        Matcher matcher = IMG.matcher(message);
        while (matcher.find())
        {
            builder.append(message.subSequence(last, matcher.start()));
            Matcher contents = CONTENTS.matcher(matcher.group(1));
            Map<String, String> attributes = new LinkedHashMap<String, String>();
            while (contents.find())
            {
                attributes.put(encoder.unencode(contents.group(1)), encoder.unencode(contents.group(2)));
            }
            if (embeder.shouldEmbed(attributes))
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                String src = attributes.get("src");
                Image image = mapOfImages.get(src);
                if (image == null)
                {
                    URL url;
                    try
                    {
                        url = new URL(src);
                    }
                    catch (MalformedURLException e)
                    {
                        throw new WasteException(115, e, src);
                    }
                    String contentType;
                    try
                    {
                        URLConnection connection = url.openConnection();
                        InputStream in = connection.getInputStream();
                        int read;
                        byte[] b = new byte[1024 * 8];
                        while ((read = in.read(b)) != -1)
                        {
                            out.write(b, 0, read);
                        }
                        contentType = connection.getContentType();
                    }
                    catch (IOException e)
                    {
                        throw new WasteException(116, e, src);
                    }
                    MessageDigest md;
                    try
                    {
                        md = MessageDigest.getInstance("MD5");
                    }
                    catch (NoSuchAlgorithmException e)
                    {
                        throw new RuntimeException(e);
                    }
                    byte[] bytes = out.toByteArray();
                    md.update(bytes);
        
                    // http://www.itjungle.com/mgo/mgo030703-story01.html
        
                    StringBuffer hexString = new StringBuffer();
                    byte[] digest = md.digest();
                    for (int i = 0; i < digest.length; i++)
                    {
                        String hex = Integer.toHexString(0xFF & digest[i]);
        
                        if (hex.length() < 2)
                        {
                            hex = "0" + hex;
                        }
        
                        hexString.append(hex);
                    }
        
                    String identifier = hexString.toString();
        
                    attributes.put("src", "cid:" + identifier);
        
                    image = new Image(identifier, bytes, contentType);
                    mapOfImages.put(src, image);
                }
                listOfImages.add(image);

                builder.append("<img");
                for (Map.Entry<String, String> entry : attributes.entrySet())
                {
                    builder.append(" ");
                    builder.append(encoder.encode(entry.getKey()));
                    builder.append("=\"");
                    builder.append(encoder.encode(entry.getValue()));
                    builder.append("\"");
                }
                builder.append("/>");
            }
            else
            {
                builder.append(message.subSequence(matcher.start(), matcher.end()));
            }
            last = matcher.end();
        }
        builder.append(message.subSequence(last, message.length()));
        return builder.toString();
    }

    private String links(CharSequence message, Encoder encoder, Linker linker, Object model)
    {
        int last = 0;
        StringBuilder builder = new StringBuilder();
        Matcher matcher = A.matcher(message);
        while (matcher.find())
        {
            builder.append(message.subSequence(last, matcher.start()));
            Matcher contents = CONTENTS.matcher(matcher.group(1));
            Map<String, String> attributes = new LinkedHashMap<String, String>();
            while (contents.find())
            {
                attributes.put(encoder.unencode(contents.group(1)), encoder.unencode(contents.group(2)));
            }
            linker.attributes(model, attributes);
            builder.append("<a");
            for (Map.Entry<String, String> entry : attributes.entrySet())
            {
                builder.append(" ");
                builder.append(encoder.encode(entry.getKey()));
                builder.append("=\"");
                builder.append(encoder.encode(entry.getValue()));
                builder.append("\"");
            }
            builder.append(">");
            last = matcher.end();
        }
        builder.append(message.subSequence(last, message.length()));
        return builder.toString();
    }
}

/* vim: set et sw=4 ts=4 ai tw=78 nowrap: */