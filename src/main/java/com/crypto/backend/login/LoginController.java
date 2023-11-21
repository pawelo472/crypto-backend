package com.crypto.backend.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class LoginController {
    private UserRepository repository;
    @GetMapping("/loginn")
    public List<UserEntity> loginUser(){
        return repository.findAll();
    }
}
