package cx.it.northernmostposeidon.songcloud.servlet;

import static cx.it.northernmostposeidon.songcloud.DropboxUtils.getAuthenticator;
import com.dropbox.client.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AuthServlet extends HttpServlet {
    public static final String ATTRIBUTE_AUTH_TOKEN = "auth";
	public static final String TOKEN_PARAMETER = "oauth_token";
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String token = req.getParameter(TOKEN_PARAMETER);
			if(token != null) {
				handleAuth(token, req.getSession());
				resp.sendRedirect("http://localhost:8080/dropbox");
			}
			else {
				Authenticator auth = getAuthenticator();
				String url = auth.retrieveRequestToken("http://localhost:8080/auth");
				resp.sendRedirect(url);
			}
        } catch (Exception e) {
			e.printStackTrace();
        }
    }

	protected void handleAuth(String token, HttpSession session) {
		try {
			Authenticator auth = getAuthenticator();
			auth.retrieveAccessToken(token);
            session.setAttribute(ATTRIBUTE_AUTH_TOKEN, auth);
			//System.out.println("token: " + token);
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}
}

