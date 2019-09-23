package com.daca.listapramim.api.config;

import com.daca.listapramim.api.user.UserModel;
import com.daca.listapramim.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup;
    private UserRepository userRepository;

    @Autowired
    public InitialDataLoader(
            UserRepository userRepository) {
        this.userRepository = userRepository;
        this.alreadySetup = false;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }



        // User Admin
        UserModel user = new UserModel("Administrador","administrador@email.com", "teste");
        user = createUserIfNotFound(user);

    }


    private UserModel createUserIfNotFound(final UserModel userModel) {
        UserModel savedUser = userRepository.findByEmail(userModel.getEmail());
        if (savedUser == null) {
            savedUser = userRepository.save(userModel);
        }
        return savedUser;
    }

}