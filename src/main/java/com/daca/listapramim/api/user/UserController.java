package com.daca.listapramim.api.user;


import com.daca.listapramim.api.security.AccountCredentials;
import com.daca.listapramim.api.security.JWTLoginFilter;
import com.daca.listapramim.api.user.DTO.UserOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;1
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/preco")
@Api(tags = "Users")
@CrossOrigin
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private JWTLoginFilter jwt;
    private UserService userService;


    public ResponseEntity login(){
        
    }
}
