package com.tts.usersapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.tts.usersapi.model.User;
import com.tts.usersapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    
    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(value="state", required = false) String state) {
        
        if(state != null) {
            return userRepository.findByState(state);
        } else {
            return (List<User>) userRepository.findAll();
        }
        
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable(value="id") Long id) {
        
       

        Optional<User> user = userRepository.findById(id);
        
        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);

        // if(user.isPresent()) {
        //     return user.get();
        // } else {
        //     User userError = new User();
        //     userError.setFirstName("Error");
        //     userError.setLastName("Error");
        //     userError.setState("Error");
        //     userError.setId((long) 1000000);
        //     return userError;
        // }

    }

    @PostMapping("/users")                                                  //BindingResult is class that accepts the error
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        System.out.println("Binding Result -- : " + bindingResult);

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
        }

        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> editUser(@PathVariable(value="id") Long id, @RequestBody @Valid User user, BindingResult bindingResult) {
        
        Optional<User> requestedUser = userRepository.findById(id);

        if(!requestedUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
        }

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }




}