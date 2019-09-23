package com.daca.listapramim.api.user;


import com.daca.listapramim.api.security.AccountCredentials;
import com.daca.listapramim.api.security.JWTLoginFilter;
import com.daca.listapramim.api.security.TokenAuthenticationService;
import com.daca.listapramim.api.user.DTO.UserOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/")
@Api(tags = "Users")
@CrossOrigin
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private UserService userService;

    @Autowired
    public UserController( UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/login/","/login"})
    @ApiOperation(value ="Login an user")
    public ResponseEntity login(@RequestBody @Valid AccountCredentials accountCredentials){
        LOGGER.info("tentando logar o usuário com email "+ accountCredentials.getUsername());

        UserModel user = userService.login(accountCredentials.getUsername(), accountCredentials.getPassword());
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        LOGGER.info("user logado");
        HttpHeaders headers = new HttpHeaders();
        headers.add("authotization", userService.generateToken(user));
        UserOutput saida = new UserOutput();
        saida.setNome(user.getNome());
        saida.setEmail(user.getEmail());
        return ResponseEntity.ok().headers(headers).body(saida);
        
    }

    @PostMapping({"/logout/", "/logout"})
    @ApiOperation(value = "Logout an User by token")
    public ResponseEntity<?> logoutUser(
            @RequestHeader(value = TokenAuthenticationService.HEADER) String token) {
        LOGGER.info("trying logout user");
        userService.logout(token);
        LOGGER.info("logout");
        return ResponseEntity.noContent().build();
    }
}
