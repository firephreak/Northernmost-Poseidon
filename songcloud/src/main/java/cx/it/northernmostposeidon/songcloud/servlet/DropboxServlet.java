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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	    ArrayList<String> songs = new ArrayList<String>();

        try {
            DropboxClient client = getClient((Authenticator)req.getSession().getAttribute("auth"));

            Map accountInfo = client.accountInfo(true, null);
            Map body = (Map) accountInfo.get("body");
            String uid = body.get("uid").toString();

            Map metadata = client.metadata("dropbox", "/Public/music",
                           100, null, true, false, null);
            JSONArray contents = (JSONArray) metadata.get("contents");

            String url = "https://dl.dropbox.com/u/" + uid;

            for (Object c : contents) {
                String path = (String) ((Map) c).get("path");
                songs.add(url + path.substring(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

	    req.setAttribute("songs", songs.toArray());
	    req.getRequestDispatcher(PLAYER_VIEW).forward(req, resp);
    }
}
