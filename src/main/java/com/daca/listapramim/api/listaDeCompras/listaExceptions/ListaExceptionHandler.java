package com.daca.listapramim.api.listaDeCompras.listaExceptions;


import com.daca.listapramim.api.item.itemExceptions.ItemDuplicateException;
import com.daca.listapramim.api.item.itemExceptions.ItemNotFoundException;
import com.daca.listapramim.api.utils.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class ListaExceptionHandler {

    @ExceptionHandler(ListaDeComprasDuplicateException.class)
    protected ResponseEntity handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Lista de compras com essa descrição já existente";
        return handleExceptionInternal(ex, new ApiError( HttpStatus.CONFLICT,bodyOfResponse, ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ListaDeCompraNotFoundException.class)
    protected ResponseEntity handleItemNotFound(RuntimeException ex, WebRequest request){
        return handleExceptionInternal(ex, new ApiError(HttpStatus.NOT_FOUND, "Lista de compras não encontrada", ex.getMessage()),
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
