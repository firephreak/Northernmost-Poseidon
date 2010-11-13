package cx.it.northernmostposeidon.songcloud.servlet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthServlet extends HttpServlet {
    public static final String AUTH_FORM_VIEW = "/WEB-INF/jsp/authForm.jsp";
    public static final String AUTH_RESULTS_VIEW = "/WEB-INF/jsp/authResults.jsp";
    public static final String AUTH_URL_ATTRIBUTE = "authUrl";
    public static final String REQUEST_TOKEN_ATTRIBUTE = "requestToken";
    public static final String PIN_PARAM = "pin";

}
