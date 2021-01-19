package br.com.gerenciapedidos.payments.config;


import br.com.gerenciapedidos.payments.domain.entity.User;
import br.com.gerenciapedidos.payments.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

//executa assim que o sistema e iniciado. (static)
@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            this.createUsers("William1", "will@gmail.com", "12345");
            this.createUsers("William2", "will@gmail.com", "12345");
            this.createUsers("William3", "will@gmail.com", "12345");
            this.createUsers("William4", "will@gmail.com", "12345");
        }
    }

    public void createUsers(String name, String email, String password){

        User user = new User(name, email, password);
        userRepository.save(user);

    }
}
