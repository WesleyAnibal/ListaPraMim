package com.daca.listapramim.api.user;

import com.daca.listapramim.api.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserListener {

    @Autowired
    CryptoUtil cryptoUtil;

    @PrePersist
    public void methodExecuteBeforeCreate(final UserModel user) {
        user.setPassword(cryptoUtil.hashPassword(user.getPassword()));
    }

    @PreUpdate
    public void methodExecuteBeforeUpdate(final UserModel user) {
        user.setPassword(cryptoUtil.hashPassword(user.getPassword()));
    }
}
