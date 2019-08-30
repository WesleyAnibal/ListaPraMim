package com.daca.listapramim.api.utils;

import com.daca.listapramim.api.item.itemExceptions.ItemDuplicateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { ItemDuplicateException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Item já existente";
        return handleExceptionInternal(ex, new ApiError(bodyOfResponse),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(body, headers, status);
    }
}
