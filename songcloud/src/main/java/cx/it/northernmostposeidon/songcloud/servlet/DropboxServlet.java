package cx.it.northernmostposeidon.songcloud.servlet;

import static cx.it.northernmostposeidon.songcloud.DropboxUtils.getClient;
import com.dropbox.client.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DropboxServlet extends HttpServlet {

	public static final String PLAYER_VIEW = "/WEB-INF/jsp/player.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
		try {                     
			DropboxClient client = getClient(req.getSession());

            Map accountInfo = client.accountInfo(true, null);
            String uid = (((Map)accountInfo.get("body")).get("uid")).toString();
            System.out.println(uid);

            Map metadata = client.metadata("dropbox", "/Public/music", 100, null, true, false, null);

            //System.out.println(((JSONArray)(metadata.get("contents"))).length());

            ArrayList<String> songs = new ArrayList<String>();

            for (Object o : (JSONArray)(metadata.get("contents")))
            {
                String tempString = ((String)((Map)o).get("path"));

                tempString = tempString.substring(7);

                tempString = "https://dl.dropbox.com/u/" + uid + tempString;

                songs.add(tempString);
            }

            System.out.println(songs);

            req.setAttribute("songs", songs.toArray());
			req.getRequestDispatcher(PLAYER_VIEW).forward(req, resp);
            
        } catch (Exception e) {
			e.printStackTrace();
        }
    }
}
