package com.tareq.springboot.errors;

import org.springframework.http.HttpStatus;

public class ArgumentException extends ApiBaseException{

    public ArgumentException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }
}
