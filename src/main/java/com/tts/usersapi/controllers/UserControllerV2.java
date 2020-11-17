package com.tts.usersapi.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/v2")
@Api(value = "userinfo", description = "Operations pertaining to user information")
public class UserControllerV2 {
    

    @Autowired
    UserRepository userRepository;
    


    @ApiOperation(value = "Get all user info", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "BAD REQUEST"),
        @ApiResponse(code = 200, message = "OK"),
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required = true) String state) {
        
        if(state != null) {
            return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<User>>(HttpStatus.OK);
        
    }



    @ApiOperation(value = "Get all user info", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 200, message = "OK")
        
    })
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



    @ApiOperation(value = "Create user")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 201, message = "CREATED")
        
    })
    @PostMapping("/users")                                                  //BindingResult is class that accepts the error
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        System.out.println("Binding Result -- : " + bindingResult);

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
        }

        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }





    @ApiOperation(value = "Get all user info", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 200, message = "OK")
        
    })
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

        return new ResponseEntity<>(HttpStatus.OK);
    }




    @ApiOperation(value = "Get all user info", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 200, message = "OK")
        
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        
        Optional<User> requestedUser = userRepository.findById(id);

        if(!requestedUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }




}