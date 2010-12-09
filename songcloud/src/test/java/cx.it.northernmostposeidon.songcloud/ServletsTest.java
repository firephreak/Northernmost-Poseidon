package cx.it.northernmostposeidon.songcloud.unittests;

import static cx.it.northernmostposeidon.songcloud.DropboxUtils.*;
import static cx.it.northernmostposeidon.songcloud.UrlBuilder.BuildURL;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import cx.it.northernmostposeidon.songcloud.servlet.*;
import com.dropbox.client.*;

import org.apache.xpath.operations.String;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import java.net.URL;

public class ServletsTest {
    @Test
    public void testHomepage() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://localhost:8080");
        Assert.assertEquals("SongCloud", page.getTitleText());
    }

    @Test
    public void testAuthPage() throws Exception {
        final WebClient webClient = new WebClient();
        final WebRequest webRequest = new WebRequest(new URL("http://localhost:8080/auth"));
        final WebResponse webResponse = webClient.loadWebResponse(webRequest);

        URL dropbox_url = webResponse.getWebRequest().getUrl();
        Assert.assertEquals(dropbox_url.getHost(), "www.dropbox.com");

        final HtmlPage page = webClient.getPage("http://localhost:8080/auth");
        final HtmlForm form = (HtmlForm)page.getHtmlElementById("form");

        final HtmlTextInput email = form.getInputByName("login_email");
        final HtmlPasswordInput password = form.getInputByName("login_password");

        final HtmlSubmitInput submit = form.getInputByName("login_submit");

        email.setValueAttribute("dwc@colorado.edu");
        password.setValueAttribute("songcloud");

        final HtmlPage player_page = submit.click();
        
        Assert.assertEquals("SongCloud Player", player_page.getTitleText());

        webClient.closeAllWindows();
    }

    @Test
    public void testPlayerPage() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://localhost:8080/dropbox");
        Assert.assertEquals("SongCloud Player", page.getTitleText());
    }
}