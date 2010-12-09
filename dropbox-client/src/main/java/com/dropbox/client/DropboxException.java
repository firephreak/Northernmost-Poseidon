package com.dropbox.client;

public class DropboxException extends Exception {
    /**
     * To make it easier on the developer, we wrap all of the many many many checked
     * exceptions that every library insists on throwing in this one exception.
     */
    private static final long serialVersionUID = 1L;

    public DropboxException(Throwable e) {
        super(e);
    }

    public DropboxException(String string) {
        super(string);
    }
}
