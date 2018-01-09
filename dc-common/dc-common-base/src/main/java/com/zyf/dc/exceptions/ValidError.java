package com.zyf.dc.exceptions;

public class ValidError extends RuntimeException {

    public ValidError(String msg) {
        super(msg);
    }

    public ValidError() {
    }
}