package com.daca.listapramim.api.user.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "userInput")
public class UserInput {


    @ApiModelProperty(example = "Anibal", required = true)
    @NotEmpty
    private String nome;

    @ApiModelProperty(example = "Anibal", required = true)
    @NotEmpty
    private String email;

    @ApiModelProperty(example = "1234", required = true)
    @NotEmpty
    private String password;

    public UserInput() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
