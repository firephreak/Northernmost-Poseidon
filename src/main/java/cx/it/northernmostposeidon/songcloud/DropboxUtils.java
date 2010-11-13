package cx.it.northernmostposeidon.songcloud;

import com.dropbox.client.*;
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
		
		System.out.println("consumer key: " + (String)config.get("consumer_key"));
		System.out.println("consumer secret: " + (String)config.get("consumer_secret"));
		System.out.println("request token url: " + (String)config.get("request_token_url"));
		System.out.println("access token url: " + (String)config.get("access_token_url"));
		System.out.println("authorization url: " + (String)config.get("authorization_url"));
		
		return auth;
	}
	
	public static DropboxClient getClient() {
		if(client == null) {
			client = new DropboxClient(config, auth);
		}
		return client;
	}
}
