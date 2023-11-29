package com.crypto.backend.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public String addUser(UserDTO userDTO) {
        // Sprawdzenie, czy istnieje już użytkownik o danej nazwie użytkownika lub adresie e-mail
        if (userRepo.existsByUsername(userDTO.getUsername())) {
            // Obsługa sytuacji, gdy istnieje już użytkownik o danej nazwie użytkownika
            return String.valueOf(new LoginMesage("Username already exists", false));
        }

        if (userRepo.existsByEmail(userDTO.getEmail())) {
            // Obsługa sytuacji, gdy istnieje już użytkownik o danym adresie e-mail
            return String.valueOf(new LoginMesage("Email already exists", false));

        }

        // Jeśli użytkownik o danej nazwie użytkownika i adresie e-mail nie istnieje, to dodaj go do bazy danych
        UserEntity user = new UserEntity(
                userDTO.getUserid(),
                userDTO.getUsername(),
                this.passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getEmail(),
                userDTO.getApikey()
        );

        userRepo.save(user);

        return user.getUsername();
    }

    UserDTO userDTO;

    @Override
    public LoginMesage loginUser(LoginDTO loginDTO) {
        String msg = "";
        UserEntity user1 = userRepo.findByUsername(loginDTO.getUsername());
        if (user1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<UserEntity> user = userRepo.findOneByUsernameAndPassword(loginDTO.getUsername(), encodedPassword);
                if (user.isPresent()) {

                    return new LoginMesage("Login Success", true);
                } else {
                    return new LoginMesage("Login Failed", false);
                }
            } else {

                return new LoginMesage("password Not Match", false);
            }
        }else {
            return new LoginMesage("Email not exits", false);
        }


    }

}
