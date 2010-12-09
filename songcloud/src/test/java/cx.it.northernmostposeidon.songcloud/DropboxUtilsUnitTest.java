package cx.it.northernmostposeidon.songcloud.unittests;

import static cx.it.northernmostposeidon.songcloud.DropboxUtils.*;
import cx.it.northernmostposeidon.songcloud.servlet.*;
import com.dropbox.client.*;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

public class DropboxUtilsUnitTest {
    private Authenticator auth;
    private DropboxClient client;

    @Test
    public void testGetAuthenticator() {
        Assert.assertNull(auth);
        auth = getAuthenticator();
        Assert.assertNotNull(auth);
    }

    @Test
    public void testGetClient() {
        Assert.assertNull(client);
        auth = getAuthenticator();
        try {
            auth.retrieveRequestToken(AuthServlet.AUTH_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client = getClient(auth);
        Assert.assertNotNull(client);
    }
}