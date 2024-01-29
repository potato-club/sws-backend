package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class EmailDuplicationException extends BusinessException{
    public EmailDuplicationException(ErrorCode errorCode, String message){
        super(message, errorCode);
    }
}