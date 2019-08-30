package com.daca.listapramim.api.listaDeCompras.listaExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)  // 404
public class ListaDeComprasDuplicateException extends RuntimeException {

    public ListaDeComprasDuplicateException() {
        super("Lista de compras com essa descrição já existente");
    }
}
