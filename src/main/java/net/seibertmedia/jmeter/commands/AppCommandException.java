package net.seibertmedia.jmeter.commands;

public class AppCommandException extends Exception {
    public AppCommandException() {
    }

    public AppCommandException(String message) {
        super(message);
    }

    public AppCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppCommandException(Throwable cause) {
        super(cause);
    }

    public AppCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
