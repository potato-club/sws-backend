package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class PostNotFoundException extends BusinessException{
    public PostNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}