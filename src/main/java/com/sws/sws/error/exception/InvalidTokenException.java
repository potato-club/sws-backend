package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class InvalidTokenException  extends BusinessException {

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}