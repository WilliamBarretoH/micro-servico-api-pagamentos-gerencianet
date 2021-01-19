package br.com.gerenciapedidos.payments.service.interfaces;

import br.com.gerenciapedidos.payments.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> listUsers();

    Optional<User> findById(Long id);

    User saveUser(User user);
}
