package com.project.bookreport.exception.custom_exceptions;

import com.project.bookreport.exception.ErrorCode;

public class BookException extends CustomException {
    public BookException(ErrorCode errorCode){super(errorCode);}
}
