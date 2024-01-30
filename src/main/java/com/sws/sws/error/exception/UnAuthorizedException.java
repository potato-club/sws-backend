package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class UnAuthorizedException  extends BusinessException {

    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}