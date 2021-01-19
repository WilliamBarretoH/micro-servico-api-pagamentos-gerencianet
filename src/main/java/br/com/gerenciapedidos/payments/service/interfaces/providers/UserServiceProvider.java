package br.com.gerenciapedidos.payments.service.interfaces.providers;

import br.com.gerenciapedidos.payments.domain.entity.User;
import br.com.gerenciapedidos.payments.domain.repository.UserRepository;
import br.com.gerenciapedidos.payments.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceProvider implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
