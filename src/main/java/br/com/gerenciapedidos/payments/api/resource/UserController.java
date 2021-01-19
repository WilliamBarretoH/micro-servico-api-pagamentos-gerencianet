package br.com.gerenciapedidos.payments.api.resource;



import br.com.gerenciapedidos.payments.api.builder.UserBuilder;
import br.com.gerenciapedidos.payments.api.dto.UserDto;
import br.com.gerenciapedidos.payments.domain.entity.User;

import br.com.gerenciapedidos.payments.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired(required = true)
    UserService userService;

    @Autowired
    UserBuilder userBuilder;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listUSers(){
        List<User> users = userService.listUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable(value = "userId") Long userId){
        Optional<User> user = userService.findById(userId);

        if(user.isPresent()) return new ResponseEntity<User>(user.get(), HttpStatus.OK);

        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody UserDto userDto){

        User userBuilded = userBuilder.buildUser(userDto);

        User user = userService.saveUser(userBuilded);
        return new ResponseEntity(user, HttpStatus.CREATED);

    }

}
