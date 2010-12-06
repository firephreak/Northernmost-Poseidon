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
        final URL url = new URL(songURL);
        final URLConnection conn = url.openConnection();
        final InputStream is = new BufferedInputStream(url.openStream());

        String filename = url.getFile();
        filename = filename.substring(filename.lastIndexOf('/') + 1);
	File file = new File(filename);
        final FileOutputStream os = new FileOutputStream(file);

	final Object[] status = new Object[] { null };

	new Thread(new Runnable() {
	    public void run() {
		try {
		    byte[] data = new byte[conn.getContentLength()];
		    int read = 0, offset = 0;
		    while (status[0] == null && offset < data.length) {
			read = is.read(data, offset, data.length - offset);
			if (read == -1) {
			    break;
			} else {
			    os.write(data, offset, read);
			    os.flush();
			    offset += read;
			}
		    }

		    System.out.printf("ID3: read %d/%d = %.2f%%\n", 
                        offset, data.length, offset / (float) data.length);
		
		    is.close();
		    os.close();

		    status[0] = offset == data.length;
		} catch (Exception e) {
		    /* yep. */
		}
	    }
	}).start();
	
        String name = "Error!", artist = "Error!", album = "Error!";
	boolean finished = false;
	
	while (!finished) {
	    try {
		MediaFile mf = new MP3File(file);
		ID3Tag[] tags = mf.getTags();
		for (ID3Tag tag : tags) {
		    if (tag instanceof ID3V2Tag) {
			ID3V2Tag t = (ID3V2Tag) tag;
			name = t.getTitle();
			artist = t.getArtist();
			album = t.getAlbum();
			status[0] = true;
			finished = true;
			break;
		    } else if (tag instanceof ID3V1Tag) {
			ID3V1Tag t = (ID3V1Tag) tag;
			name = t.getTitle();
			artist = t.getArtist();
			album = t.getAlbum();
			status[0] = true;
			finished = true;
			break;
		    } else if (status[0] != null) {
			finished = true;
			break;
		    }
		}
	    } catch (ID3Exception e) {
		/* yep. */
	    }
	    try {
		Thread.sleep(100);
	    } catch (Exception e) {
		/* yep. */
	    }
	}

	file.delete();

        return new String[] { name, artist, album };
    }
}
