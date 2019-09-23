package com.daca.listapramim.api.user;

import com.daca.listapramim.api.security.SecurityFilter;
import com.daca.listapramim.api.security.TokenAuthenticationService;
import com.daca.listapramim.api.utils.CryptoUtil;
import com.daca.listapramim.api.utils.GenericService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserModel login(String login, String credentials){
        UserModel user = repository.findByEmail(login);
        if(user != null && cryptoUtil.matches(credentials, user.getPassword())){
            user.setPassword(null);
            return user;
        }
        return null;
    }


    public String generateToken(UserModel user){
        return tokenAuthenticationService.generateToken(user);
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
