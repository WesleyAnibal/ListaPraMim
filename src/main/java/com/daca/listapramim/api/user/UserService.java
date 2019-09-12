package com.daca.listapramim.api.user;

import com.daca.listapramim.api.security.SecurityFilter;
import com.daca.listapramim.api.security.TokenAuthenticationService;
import com.daca.listapramim.api.utils.CryptoUtil;
import com.daca.listapramim.api.utils.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<Long, UserModel, UserRepository> {

    private CryptoUtil cryptoUtil;

    private SecurityFilter securityFilter;

    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public UserService(
            CryptoUtil cryptoUtil,
            SecurityFilter securityFilter,
            TokenAuthenticationService tokenAuthenticationService) {
        this.cryptoUtil = cryptoUtil;
        this.securityFilter = securityFilter;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

}
