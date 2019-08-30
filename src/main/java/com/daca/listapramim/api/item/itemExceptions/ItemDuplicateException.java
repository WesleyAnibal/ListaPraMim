package com.daca.listapramim.api.item.itemExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)  // 404
public class ItemDuplicateException extends  RuntimeException{

    public ItemDuplicateException() {
        super("Item já existente");
    }
}
