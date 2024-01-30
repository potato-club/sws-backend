package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class InternerServerException extends BusinessException {

    public InternerServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}