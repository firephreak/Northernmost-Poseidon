package cx.it.northernmostposeidon.songcloud.servlet;

import static cx.it.northernmostposeidon.songcloud.DropboxUtils.getClient;
import com.dropbox.client.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class DropboxServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
		try {
			DropboxClient client = getClient();
		    Map accountInfo = client.accountInfo(true, null);
		    System.out.println(accountInfo);
        } catch (Exception e) {
			e.printStackTrace();
        }
    }
}
