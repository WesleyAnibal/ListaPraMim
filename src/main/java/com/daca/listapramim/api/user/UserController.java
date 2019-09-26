package com.daca.listapramim.api.user;


import com.daca.listapramim.api.item.DTO.ItemOutput;
import com.daca.listapramim.api.precos.DTO.PrecoInput;
import com.daca.listapramim.api.precos.MapaDePreco;
import com.daca.listapramim.api.security.AccountCredentials;
import com.daca.listapramim.api.security.JWTLoginFilter;
import com.daca.listapramim.api.security.TokenAuthenticationService;
import com.daca.listapramim.api.user.DTO.UserIO;
import com.daca.listapramim.api.user.DTO.UserInput;
import com.daca.listapramim.api.user.DTO.UserOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@Api(tags = "Users")
@CrossOrigin
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private UserService userService;
    private UserIO userIO;

    @Autowired
    public UserController(UserService userService, UserIO userIO) {
        this.userService = userService;
        this.userIO = userIO;
    }

    @PostMapping({"/login/","/login"})
    @ApiOperation(value ="Login an user")
    public ResponseEntity login(@RequestBody @Valid AccountCredentials accountCredentials){
        LOGGER.info("tentando logar o usuário com email "+ accountCredentials.getUsername());

        UserModel user = userService.login(accountCredentials.getUsername(), accountCredentials.getPassword());
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = getHttpHeaders(user);
        UserOutput saida = this.userIO.mapTo(user);
        return ResponseEntity.ok().headers(headers).body(saida);
    }

    private HttpHeaders getHttpHeaders(UserModel user) {
        LOGGER.info("user logado");
        HttpHeaders headers = new HttpHeaders();
        headers.add("authotization", userService.generateToken(user));
        return headers;
    }


    @PostMapping({"/",""})
    @ApiOperation(value = "Create User")
    public ResponseEntity create(@Valid @RequestBody UserInput userInput){
        LOGGER.info("Criando User");
        UserModel user = this.userIO.mapTo(userInput);
        this.userService.createTransaction(user);
        LOGGER.info("User criado");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*@ApiOperation(value =  "Get all Users")
    @GetMapping({"/", ""})
    public List<ItemOutput> index(){
        LOGGER.info("Index Users");
        Type type = new TypeToken<List<ItemOutput>>() {}.getType();
        LOGGER.info("Index Users");
        return this.userIO.toList(this.userService.(nome, categoria), type);
    }
*/

    /*@PostMapping({"/logout/", "/logout"})
    @ApiOperation(value = "Logout an User by token")
    public ResponseEntity<?> logoutUser(
            @RequestHeader(value = TokenAuthenticationService.HEADER) String token) {
        LOGGER.info("trying logout user");
        userService.logout();
        HttpHeaders headers = new HttpHeaders();
        headers.clear();
        LOGGER.info("logout");
        return ResponseEntity.noContent().build();
    }*/
}
