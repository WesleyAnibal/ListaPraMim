package com.daca.listapramim.api.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "AccountInput")
public class AccountCredentials {


    @ApiModelProperty(example = "administrador@email.com", required = true)
    @NotEmpty
    private String username;

    @ApiModelProperty(example = "teste", required = true)
    @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
