package com.daca.listapramim.api.user;


import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;

@ApiIgnore
public enum Privilege implements Serializable {


    US("US", "USER"),
    IT("IT", "ITEM"),
    PR("PR", "PRECO"),
    LC("LC", "LISTA_DE_COMPRAS");


    private String key;
    public String name;


    Privilege(String key, String name){
        this.key = key;
        this.name = name;
    }

    public static Privilege fromKey(String key) {
        for (Privilege categoria : Privilege.values()) {
            if (categoria.key.equalsIgnoreCase(key))
                return categoria;
        }
        throw new RuntimeException("Privilégio " + key + " não existe");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
