package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }

}
