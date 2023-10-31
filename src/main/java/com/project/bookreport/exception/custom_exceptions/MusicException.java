package com.project.bookreport.exception.custom_exceptions;

import com.project.bookreport.exception.ErrorCode;

public class MusicException extends CustomException{
    public MusicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
