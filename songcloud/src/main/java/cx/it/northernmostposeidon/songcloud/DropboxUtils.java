package cx.it.northernmostposeidon.songcloud;

import com.dropbox.client.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class DropboxUtils {
    private static Map config;
	private static Authenticator auth;
	private static DropboxClient client;
	
	public static Authenticator getAuthenticator() {
		if(auth == null) {
			try {
				config = Authenticator.loadConfig("config/testing.json");
				
				auth = new Authenticator(config);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return auth;
	}

	public static DropboxClient getClient(Authenticator auth) {
		if(auth != null) {
			client = new DropboxClient(config, auth);
		}
		return client;
	}
}
