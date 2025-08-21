package com.agilesolutions.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class BusinessException extends RuntimeException {

    private final static long serialVersionUID = 1l;

    @Getter
    private List<Problem> problems = new ArrayList<>();

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }


    public BusinessException(String format, Throwable cause, Object ... args) {
        this(format(format,args), cause);
    }



}
