package com.daca.listapramim.api.item.itemExceptions;


import com.daca.listapramim.api.utils.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class ItemExceptionHandler {


    @ExceptionHandler(ItemDuplicateException.class)
    protected ResponseEntity handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Item já existente";
        return handleExceptionInternal(ex, new ApiError( HttpStatus.CONFLICT,bodyOfResponse, ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    protected ResponseEntity handleItemNotFound(RuntimeException ex, WebRequest request){
        return handleExceptionInternal(ex, new ApiError(HttpStatus.NOT_FOUND, "Item não encontrado", ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
