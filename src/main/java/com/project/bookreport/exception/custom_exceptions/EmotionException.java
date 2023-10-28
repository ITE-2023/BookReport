package com.project.bookreport.exception.custom_exceptions;

import com.project.bookreport.exception.ErrorCode;

public class EmotionException  extends RuntimeException{
    private final ErrorCode errorCode;

    public EmotionException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
