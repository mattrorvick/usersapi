package com.tts.usersapi.repository;

import java.util.List;

import com.tts.usersapi.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    List<User> findByState(String state);

    
}