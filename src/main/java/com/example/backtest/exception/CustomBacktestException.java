package com.example.backtest.exception;

public class CustomBacktestException extends Exception {

    private String message;
    private String errorcode;

    public CustomBacktestException (String message) {
        super(message);
        this.message = message;
    }

    public CustomBacktestException (String message, String errorcode) {
        super(message);
        this.message = message;
        this.errorcode = errorcode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }
}
