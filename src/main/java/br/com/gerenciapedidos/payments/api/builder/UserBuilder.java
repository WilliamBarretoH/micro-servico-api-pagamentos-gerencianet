package br.com.gerenciapedidos.payments.api.builder;



import br.com.gerenciapedidos.payments.api.dto.UserDto;
import br.com.gerenciapedidos.payments.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserBuilder {

    private User user;

    public User buildUser(UserDto userDto){

        return user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
