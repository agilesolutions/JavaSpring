package com.agilesolutions.exception;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ErrorResponse toProblemDetail(Throwable throwable) {

        return ErrorResponse.builder(throwable, HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage())
                .title(throwable.getMessage())
                .type(URI.create("https://mycomp.com/whatever.html"))
                .detail(throwable.getMessage())
                .property("errorCategory", "Generic")
                .property("timestamp", Instant.now())
                .build();

    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("business validation exception");
        problemDetail.setType(URI.create("https://mycomp.com/whatever.html"));
        ex.getProblems().stream().forEach(pr -> problemDetail.setProperty(pr.getCode(), pr.getMessage()));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("jackson de-serialization error");
        problemDetail.setType(URI.create("https://mycomp.com/whatever.html"));
        problemDetail.setProperty("details", ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {


        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("bean validation error");
        problemDetail.setType(URI.create("https://mycomp.com/whatever.html"));
        ex.getBindingResult().getFieldErrors().stream().forEach(fe -> problemDetail.setProperty(fe.getField(), fe.getDefaultMessage()));
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);


    }
}
