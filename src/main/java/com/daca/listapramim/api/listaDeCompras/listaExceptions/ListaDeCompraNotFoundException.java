package com.daca.listapramim.api.listaDeCompras.listaExceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)  // 404
public class ListaDeCompraNotFoundException extends RuntimeException {

    public ListaDeCompraNotFoundException(Long id) {
        super("Lista de compras com id "+id+" não encontrada");
    }
}
