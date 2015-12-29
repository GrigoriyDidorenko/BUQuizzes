package com.bionic.exceptions;

/**
 * Created by c2411 on 29.12.2015.
 */
public class UserException extends Exception {

    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    private boolean writableStackTrace;

    public UserException() {
        super();
    }

    public UserException(String message) {
        this.message = message;
    }

    public UserException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public UserException(Throwable cause) {
        this.cause = cause;
    }

    public UserException(String message, Throwable cause,
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
