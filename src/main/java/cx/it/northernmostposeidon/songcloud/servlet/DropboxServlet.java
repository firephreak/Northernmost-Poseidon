package cx.it.northernmostposeidon.songcloud.servlet;

import static cx.it.northernmostposeidon.songcloud.DropboxUtils.getClient;
import com.dropbox.client.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DropboxServlet extends HttpServlet {

	public static final String PLAYER_VIEW = "/WEB-INF/jsp/player.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
		try {                     
			DropboxClient client = getClient(req.getSession());
			Map accountInfo = client.accountInfo(true, null);
		    System.out.println(accountInfo);

            Map res = client.metadata("dropbox", "/Public/music", 100, null, true, false, null);
            System.out.println(res);            
			
			req.getRequestDispatcher(PLAYER_VIEW).forward(req, resp);
        } catch (Exception e) {
			e.printStackTrace();
        }
    }
}
