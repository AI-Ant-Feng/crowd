package com.fengjian.crowd.exception;

public class LoginAcctAlreadyInUseExceptionForUpdate extends RuntimeException{
    public LoginAcctAlreadyInUseExceptionForUpdate() {
    }

    public LoginAcctAlreadyInUseExceptionForUpdate(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseExceptionForUpdate(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseExceptionForUpdate(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseExceptionForUpdate(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
