package com.tts.usersapi.controller;

import java.util.List;
import java.util.Optional;

import com.tts.usersapi.model.User;
import com.tts.usersapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/")
    public List<User> getUsers(@RequestParam(value="state", required = false) String state) {
        
        if(state != null) {
            return userRepository.findByState(state);
        } else {
            return (List<User>) userRepository.findAll();
        }
        
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value="id") Long id) {
        
       

        Optional<User> user = userRepository.findById(id);
        
        if(user.isPresent()) {
            return user.get();
        } else {
            User userError = new User();
            userError.setFirstName("Error");
            userError.setLastName("Error");
            userError.setState("Error");
            userError.setId((long) 1000000);
            return userError;
        }

    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {

        System.out.println(user);
        userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public void editUser(@PathVariable(value="id") Long id, @RequestBody User user) {

        userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }




}