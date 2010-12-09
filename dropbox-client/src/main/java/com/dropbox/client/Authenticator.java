package com.dropbox.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.HttpRequest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Authenticator {

    public String consumer_key = null;
    public String consumer_secret = null;
    public String request_token_url = null;
    public String access_token_url = null;
    public String authorization_url = null;
    public OAuthConsumer consumer = null;
    public OAuthProvider provider = null;
    
    // This gets rid of a warning about the Map not being a parameterized type
    @SuppressWarnings("unchecked")
    public Map config = null;

    protected Authenticator() 
    {

    }

    /**
     * Takes a Map of configuration values (similar to what's loaded by loadConfig) and configures
     * a for accessing the Dropbox service.
     *
     * You can preconfigure an access token by setting access_token_key and access_token_secret in
     * the config map.
     */
    @SuppressWarnings("unchecked")
    public Authenticator(Map config) throws IOException, OAuthException, OAuthCommunicationException 
    {
        this.config = config;
        this.consumer_key = (String)config.get("consumer_key");
        this.consumer_secret = (String)config.get("consumer_secret");
        this.request_token_url = (String)config.get("request_token_url");
        this.access_token_url = (String)config.get("access_token_url");
        this.authorization_url = (String)config.get("authorization_url");

        this.consumer = new DefaultOAuthConsumer(this.consumer_key, this.consumer_secret);
        this.provider = new DefaultOAuthProvider(this.request_token_url, this.access_token_url,
                this.authorization_url);

        if (config.get("access_token_key") != null) {
            assert config.get("access_token_secret") != null : "You must give the access_token_secret as well.";
            consumer.setTokenWithSecret((String)config.get("access_token_key"), (String)config.get("access_token_secret"));
        }
    }

    /**
     * A helper method that takes a JSON formatted configuration file and loads it so you can
     * have a Map for the Authenticator.
     *
     * It doesn't check that you have the proper configuration elements, but as long as the tests pass 
     * on your config file then it should work.
     */
    @SuppressWarnings("unchecked")
    public static Map loadConfig(String path) throws FileNotFoundException, IOException, ParseException {
        BufferedReader in = new BufferedReader(new FileReader(path), RESTUtility.BUFFER_SIZE);
        return loadConfig(in);
    }
    
    @SuppressWarnings("unchecked")
    public static Map loadConfig(InputStream is) throws FileNotFoundException, IOException, ParseException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is), RESTUtility.BUFFER_SIZE);
        return loadConfig(in);
    }

    @SuppressWarnings("unchecked")
    public static Map loadConfig(BufferedReader in) throws FileNotFoundException, IOException, ParseException {
        String inputLine = null;
        String result = "";

        try {
            while ((inputLine = in.readLine()) != null) 
                result += inputLine;
        } finally {
            in.close();
        }

        JSONParser parser = new JSONParser();
        return (Map)parser.parse(result);
    }

    public String retrieveRequestToken(String callback) throws IOException, OAuthException, OAuthCommunicationException 
    {
        String url = provider.retrieveRequestToken(consumer, callback);
        return url;
    }
    
    public void retrieveAccessToken(String verifier) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException
    {
        provider.retrieveAccessToken(consumer, verifier);
    }

    public HttpURLConnection sign(URL target) throws IOException, OAuthMessageSignerException, OAuthCommunicationException, OAuthExpectationFailedException
    {
        HttpURLConnection request = (HttpURLConnection)target.openConnection();
        sign(request);
        return request;
    }

    public void sign(HttpURLConnection request) throws OAuthMessageSignerException, OAuthCommunicationException, OAuthExpectationFailedException {
        consumer.sign(request);
    }

    public void sign(HttpRequest request) throws OAuthMessageSignerException, OAuthCommunicationException, OAuthExpectationFailedException {
        // create a consumer object and configure it with the access
        // token and token secret obtained from the service provider
        OAuthConsumer cons = new CommonsHttpOAuthConsumer(this.consumer_key, this.consumer_secret);
        cons.setTokenWithSecret(getTokenKey(), getTokenSecret());

        // sign the request
        cons.sign(request);
    }

    public String getTokenKey() {
        return consumer.getToken();
    }

    public String getTokenSecret() {
        return consumer.getTokenSecret();
    }
}
