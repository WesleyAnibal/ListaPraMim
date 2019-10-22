package com.daca.listapramim.api.config;

import com.daca.listapramim.api.user.Privilege;
import com.daca.listapramim.api.user.UserModel;
import com.daca.listapramim.api.user.UserRepository;
import com.daca.listapramim.api.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup;
    private UserRepository userRepository;

    @Autowired
    private CryptoUtil cryptoUtil;
    @Autowired
    public InitialDataLoader(
            UserRepository userRepository ) {
        this.userRepository = userRepository;
        this.alreadySetup = false;

    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // User Admin
        UserModel user = new UserModel("Administrador","administrador@email.com", "teste");
        Set<Privilege> privileges = new HashSet<>();
        privileges.add(Privilege.IT);
        privileges.add(Privilege.LC);
        privileges.add(Privilege.US);
        privileges.add(Privilege.PR);
        user.setPrivileges(privileges);
        user = createUserIfNotFound(user);

    }


    private UserModel createUserIfNotFound(final UserModel userModel) {
        UserModel savedUser = userRepository.findByEmail(userModel.getEmail());
        if (savedUser == null) {
            //userModel.setPassword(cryptoUtil.hashPassword(userModel.getPassword()));
            /*TODO criar o listener de user depois*/
            savedUser = userRepository.save(userModel);
        }
        return savedUser;
    }

}