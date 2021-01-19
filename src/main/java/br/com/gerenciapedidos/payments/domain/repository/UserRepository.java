package br.com.gerenciapedidos.payments.domain.repository;


import br.com.gerenciapedidos.payments.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//interface responsavel pelas operacoes com o banco
public interface UserRepository extends JpaRepository<User, Long> {


}
