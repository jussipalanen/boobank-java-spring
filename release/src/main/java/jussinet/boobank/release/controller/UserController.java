package jussinet.boobank.release.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jussinet.boobank.release.entity.User;
import jussinet.boobank.release.repository.UserRepository;

@RestController
public class UserController {

    private UserRepository repository;

    UserController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    /**
     * Get all of the users
     * 
     * @return
     */
    @GetMapping(value = "/users")
    List<User> all() {
        return repository.findAll();
    }

    /**
     * Get single user by id
     * 
     * @param id
     * @return
     */
    @GetMapping(value = "/user/{id}")
    public Optional<User> get(@PathVariable int id) {
        return repository.findById(id);
    }
}
