package com.daca.listapramim.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component("cryptoUtil")
public class CryptoUtil {

     private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CryptoUtil() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean matches(String password, String hash) {
        return bCryptPasswordEncoder.matches(password, hash);
    }

}
