package com.dropbox.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.json.simple.JSONArray;

/**
 * High-level API for Dropbox functions that wraps the calls in DropboxClient.
 * @author tom
 *
 */
public class DropboxAPI {
    protected DropboxClient mClient;
    protected Config mConfig;

    final public static int AUTH_STATUS_NONE = -1;
    final public static int AUTH_STATUS_FAILURE = 0;
    final public static int AUTH_STATUS_SUCCESS = 1;
    final public static int AUTH_STATUS_NETWORK_ERROR = 2;

    @SuppressWarnings("unchecked")
    protected DropboxClient getClient(Map config, Authenticator auth) {
        if (mClient != null) {
            return mClient;
        } else {
            mClient = new DropboxClient(config, auth);
            return mClient;
        }
    }

    @SuppressWarnings("unchecked")
    static private long getFromMapAsLong(Map map, String name) {
        Object val = map.get(name);
        long ret = 0;
        if (val != null && val instanceof Number) {
            ret = ((Number)val).longValue();
        }
        return ret;
    }
    @SuppressWarnings("unchecked")
    static private int getFromMapAsInt(Map map, String name) {
        Object val = map.get(name);
        int ret = 0;
        if (val != null && val instanceof Number) {
            ret = ((Number)val).intValue();
        }
        return ret;
    }
    @SuppressWarnings("unchecked")
    static private boolean getFromMapAsBoolean(Map map, String name) {
        Object val = map.get(name);
        if (val != null && val instanceof Boolean) {
            return ((Boolean)val).booleanValue();
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    static private void addToMapIfSet(Map map, String name, String val) {
        if (val != null) {
            map.put(name, val);
        }
    }
    @SuppressWarnings("unchecked")
    static private void addToMapIfSet(Map map, String name, long val) {
        if (val > -1) {
            map.put(name, val);
        }
    }

    public abstract class DropboxReturn {
        public int httpCode;
        public String httpReason;
        public String httpBody;
        protected boolean hasError = false;

        public DropboxReturn(HttpResponse resp) {
            StatusLine status = resp.getStatusLine();
            httpCode = status.getStatusCode();
            httpReason = status.getReasonPhrase();
        }

        @SuppressWarnings("unchecked")
        public DropboxReturn(Map map) {
            if (map != null) {
                Object error = map.get("ERROR");
                if (error != null && error instanceof StatusLine) {
                    hasError = true;
                    StatusLine status = (StatusLine)error;
                    httpCode = status.getStatusCode();
                    httpReason = status.getReasonPhrase();
                } else {
                    httpCode = 200;
                }
                String httpBody = (String)map.get("RESULT");
            }
        }

        @SuppressWarnings("unchecked")
        protected Map getBody(Map map) {
            if (map != null) {
                Object body = map.get("body");
                if (body != null && body instanceof Map) {
                    return (Map)body;
                } else if (hasError) {
                    return null;
                }
            }
            return map;
        }

        public boolean isError() {
            return (httpCode >= 300);
        }
    }

    public class Config extends DropboxReturn {
        public String consumerKey;
        public String consumerSecret;
        public String server;
        public String contentServer;
        public int port = -1;
        public String accessTokenKey;
        public String accessTokenSecret;
        public int authStatus = AUTH_STATUS_NONE;
        public String authDetail; 

        @SuppressWarnings("unchecked")
        public Config(Map map) {
            super(map);

            map = getBody(map);
            if (map != null) {
                consumerKey = (String)map.get("consumer_key");
                consumerSecret = (String)map.get("consumer_secret");
                server = (String)map.get("server");
                contentServer = (String)map.get("content_server");
                port = getFromMapAsInt(map, "port");
                accessTokenKey = (String)map.get("access_token_key");
                accessTokenSecret = (String)map.get("access_token_secret");
            }
        }

        @SuppressWarnings("unchecked")
        public Map toMap() {
            Map ret = new HashMap();

            addToMapIfSet(ret, "consumer_key", consumerKey);
            addToMapIfSet(ret, "consumer_secret", consumerSecret);
            addToMapIfSet(ret, "server", server);
            addToMapIfSet(ret, "content_server", contentServer);
            addToMapIfSet(ret, "port", port);
            addToMapIfSet(ret, "access_token_key", accessTokenKey);
            addToMapIfSet(ret, "access_token_secret", accessTokenSecret);
            return ret;
        }
    }

    public class Account extends DropboxReturn {
        public String country;
        public String displayName;
        public long quotaQuota;
        public long quotaNormal;
        public long quotaShared;
        public long uid;
        public String email;   // For internal Dropbox use; not normally set.

        @SuppressWarnings("unchecked")
        public Account(Map map) {
            super(map);

            map = getBody(map);            
            if (map != null) {
                country = (String)map.get("country");
                displayName = (String)map.get("display_name");
                uid = getFromMapAsLong(map, "uid");
                email = (String)map.get("email");

                Object quotaInfo = map.get("quota_info");
                if (quotaInfo != null && quotaInfo instanceof Map) {
                    Map quotamap = (Map)quotaInfo;
                    quotaQuota = getFromMapAsLong(quotamap, "quota");
                    quotaNormal = getFromMapAsLong(quotamap, "normal");
                    quotaShared = getFromMapAsLong(quotamap, "shared");
                }
            }
        }
    }


    public class Entry extends DropboxReturn {
        public long bytes;
        public String hash;
        public String icon;
        public boolean is_dir;
        public String modified;
        public String path;
        public String root;
        public String size;
        public String mime_type;
        public long revision;
        public boolean thumb_exists;

        public ArrayList<Entry> contents;

        @SuppressWarnings("unchecked")
        public Entry(Map map) {
            super(map);

            if (!hasError) {
                Map bmap = getBody(map);

                if (bmap != null) {
                    bytes = getFromMapAsLong(bmap, "bytes");

                    hash = (String)bmap.get("hash");
                    icon = (String)bmap.get("icon");

                    is_dir = getFromMapAsBoolean(bmap, "is_dir");

                    modified = (String)bmap.get("modified");
                    path = (String)bmap.get("path");
                    root = (String)bmap.get("root");
                    size = (String)bmap.get("size");
                    mime_type = (String)bmap.get("mime_type");

                    revision = getFromMapAsLong(bmap, "revision");

                    thumb_exists = getFromMapAsBoolean(bmap, "thumb_exists");

                    Object json_contents = bmap.get("contents");
                    if (json_contents != null && json_contents instanceof JSONArray) {
                        contents = new ArrayList<Entry>();
                        Object entry;
                        Iterator it = ((JSONArray)json_contents).iterator();
                        while (it.hasNext()) {
                            entry = it.next();
                            if (entry instanceof Map) {
                                contents.add(new Entry((Map)entry));                
                            }
                        }
                    }        
                }
            }
        }

        public String fileName() {
            String[] dirs = path.split("/");
            return dirs[dirs.length-1];
        }

        public String parentPath() {
            int ind = path.lastIndexOf('/');
            return path.substring(0, ind+1);
        }

        public String pathNoInitialSlash() {
            if (path.startsWith("/")) {
                return path.substring(1);
            } else {
                return path;
            }
        }
    }


    public class FileDownload extends DropboxReturn {
        public InputStream is;
        public HttpEntity entity;
        public String mimeType;
        public String etag;
        public long length;

        public FileDownload(HttpResponse resp) {
            super(resp);

            Header mime = resp.getFirstHeader("mime-type");
            if (mime != null) {
                mimeType = mime.getValue();
            }    
            Header et = resp.getFirstHeader("etag");
            if (et != null) {
                etag = et.getValue();
            }    

            entity = resp.getEntity();
            if (entity != null) {
                length = entity.getContentLength();
                try {
                    is = entity.getContent();
                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                }
            }
        }        
    }

    // Cached
    @SuppressWarnings("unchecked")
    public Config getConfig(InputStream is, boolean refresh) {
        if (refresh || mConfig == null) {
            try {
                Map map = Authenticator.loadConfig(is);
                mConfig = new Config(map);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mConfig;
    }

    // Used when we're able to authenticated off of a stored token
    public Config authenticateToken(String key, String secret, Config config) {
        Authenticator auth;

        // Add it to the configuration map so the authenticator gets it
        config.accessTokenKey = key;
        config.accessTokenSecret = secret;

        try {
            auth = new Authenticator(config.toMap());
            mClient = getClient(config.toMap(), auth);        
            config.authStatus = AUTH_STATUS_SUCCESS;
            return config;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            config.authStatus = AUTH_STATUS_NETWORK_ERROR;
            config.authDetail = e.toString();
            return config;
        }

    }


    // Used when we have to authenticate from scratch
    public Config authenticate(Config config, String username, String password) {
        TrustedAuthenticator auth;

        try {
            auth = new TrustedAuthenticator(config.toMap());

            boolean resp = auth.retrieveTrustedAccessToken(username, password);
            if (resp) {
                mClient = getClient(config.toMap(), auth);        

                config.accessTokenKey = auth.getTokenKey();
                config.accessTokenSecret = auth.getTokenSecret();
                config.authStatus = AUTH_STATUS_SUCCESS;
            } else {
                config.authStatus = AUTH_STATUS_FAILURE;                                        
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            config.authStatus = AUTH_STATUS_NETWORK_ERROR;                                        
            config.authDetail = e.toString();
        }

        return config;
    }

    public void deauthenticate() {
        mClient = null;
    }

    public boolean isAuthenticated() {
        if (mClient != null) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public Entry metadata(String root, String path, int file_limit, String hash, boolean list) {
        try {
            Map dirinfo = mClient.metadata(root, path, file_limit, hash, list, false, null);

            return new Entry(dirinfo);

        } catch (DropboxException de) {
            System.out.println(de.toString());
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    public Account accountInfo()    {
        try {
            Map accountInfo = mClient.accountInfo(false, null);

            return new Account(accountInfo);
        } catch (DropboxException de) {
            System.out.println(de.toString());
        }
        return null;
    }

    public FileDownload getFileStream(String root, String dbPath, String etag) {
        try {
            HttpResponse response = mClient.getFileWithVersion(root, dbPath, etag);
            return new FileDownload(response);
        } catch (DropboxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public File getFile(String root, String dbPath, File localFile, String etag) {
        FileOutputStream fos = null;
        HttpEntity entity = null;
        String result = "";
        try  {
            FileDownload response = getFileStream(root, dbPath, etag);

            entity = response.entity;

            //            localFile = openNewLocalFile(root, dbPath);
            try {
                fos = new FileOutputStream(localFile);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }        

            entity.writeTo(fos);
        } catch (SocketException se) {
            System.out.println(se.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        } finally {
            System.out.println("Result: " + result);
            try {
                if (entity != null) entity.consumeContent();
            } catch (IOException ioe) {}
            try {
                if (fos != null) fos.close();
            } catch (IOException ioe) {}

        }

        return localFile;
    }   

    public void putFile(String root, String dbPath, File localFile) {
        try {
            HttpResponse resp = mClient.putFile(root, dbPath, localFile);
        } catch (DropboxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
