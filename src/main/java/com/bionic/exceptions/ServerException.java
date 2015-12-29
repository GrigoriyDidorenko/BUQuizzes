package com.bionic.exceptions;

/**
 * Created by c2411 on 29.12.2015.
 */
public class ServerException {

    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    private boolean writableStackTrace;

    public ServerException() {
        super();
    }

    public ServerException(String message) {
        this.message = message;
    }

    public ServerException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public ServerException(Throwable cause) {
        this.cause = cause;
    }

    public ServerException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        this.message = message;
        this.cause = cause;
        this.enableSuppression = enableSuppression;
        this.writableStackTrace = writableStackTrace;
    }

    @Override
    public String toString() {
        return "UserException{" +
                "message='" + message + '\'' +
                ", cause=" + cause +
                ", enableSuppression=" + enableSuppression +
                ", writableStackTrace=" + writableStackTrace +
                '}';
    }
}
