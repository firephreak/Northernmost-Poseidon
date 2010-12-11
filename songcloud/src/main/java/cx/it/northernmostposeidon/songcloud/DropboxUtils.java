package cx.it.northernmostposeidon.songcloud;

import com.dropbox.client.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.io.File;

public class DropboxUtils {
    private static Map config;
	private static Authenticator auth;
	private static DropboxClient client;
	
	public static Authenticator getAuthenticator() {
		if(auth == null) {
			try {
				File relative = new File("config/testing.json");
				config = Authenticator.loadConfig(relative.getAbsolutePath());
				
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
