package com.daca.listapramim.api.user.DTO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "userOutput")
public class UserOutput {


    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Anibal")
    private String nome;

    @ApiModelProperty(example = "Anibal")
    private String email;

    public UserOutput() {
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

}
