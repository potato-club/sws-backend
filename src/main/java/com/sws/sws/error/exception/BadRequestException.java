package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class BadRequestException  extends BusinessException {

    public BadRequestException(String message, ErrorCode errorCode){
        super(message,errorCode);
    }
}