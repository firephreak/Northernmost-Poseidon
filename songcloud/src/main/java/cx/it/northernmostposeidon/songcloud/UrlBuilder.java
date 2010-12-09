package cx.it.northernmostposeidon.songcloud;

import javax.servlet.http.HttpServletRequest;

public class UrlBuilder {
    public static String BuildURL(HttpServletRequest req, String relative_path) {
        String s = req.getScheme() + "://" + req.getServerName() + ":";
        s += req.getServerPort() + req.getContextPath();
        s += relative_path;
        return s;
    }
}
