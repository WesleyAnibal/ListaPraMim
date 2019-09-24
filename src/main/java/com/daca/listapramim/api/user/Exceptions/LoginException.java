package com.daca.listapramim.api.user.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED)  // 404
public class LoginException extends RuntimeException {


    public LoginException() {
        super("Email ou Senha Incorretos");
    }

}
