package cx.it.northernmostposeidon.songcloud.servlet;

import java.io.*;
import java.net.*;
import javax.servlet.http.*;
import javax.servlet.*;

import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v1.*;
import org.blinkenlights.jid3.v2.*;

public class ID3Servlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
        String[] id3 = getID3Tags((String) req.getParameter("url"));
        PrintWriter out = res.getWriter();

        res.setContentType("text/xml");
        out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        out.println("<track>");
        out.println("  <name><![CDATA[" + id3[0] + "]]></name>");
        out.println("  <artist><![CDATA[" + id3[1] + "]]></artist>");
        out.println("  <album><![CDATA[" + id3[2] + "]]></album>");
        out.println("</track>");
        out.close();
    }

    private String[] getID3Tags(String songURL) throws IOException {
        URL url = new URL(songURL);
        URLConnection conn = url.openConnection();
        InputStream is = new BufferedInputStream(url.openStream());

        byte[] data = new byte[conn.getContentLength()];
        int read = 0, offset = 0;
        while (offset < data.length) {
            read = is.read(data, offset, data.length - offset);
            if (read == -1) {
                break;
            } else {
                offset += read;
            }
        }

        is.close();

        if (offset != data.length) {
            throw new IOException("only read " + offset + " bytes; expected " +
                    data.length + " bytes");
        }

        String filename = url.getFile();
        filename = filename.substring(filename.lastIndexOf('/') + 1);
        FileOutputStream os = new FileOutputStream(filename);
        os.write(data);
        os.flush();
        os.close();

        String name = "Error!", artist = "Error!", album = "Error!";

        try {
            File f = new File(filename);
            MediaFile mf = new MP3File(f);
            ID3Tag[] tags = mf.getTags();
            for (ID3Tag tag : tags) {
                if (tag instanceof ID3V2Tag) {
                    ID3V2Tag t = (ID3V2Tag) tag;
                    name = t.getTitle();
                    artist = t.getArtist();
                    album = t.getAlbum();
                    break;
                } else if (tag instanceof ID3V1Tag) {
                    ID3V1Tag t = (ID3V1Tag) tag;
                    name = t.getTitle();
                    artist = t.getArtist();
                    album = t.getAlbum();
                    break;
                } else {
                    /* yep. */
                }
            }
            f.delete();
        } catch (ID3Exception e) {
            /* yep. */
        }

        return new String[] { name, artist, album };
    }
}
