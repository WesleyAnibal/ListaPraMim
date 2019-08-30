package com.daca.listapramim.api.utils;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    private String message;

    public ApiError(String message) {
        this.message = message;
    }
}
