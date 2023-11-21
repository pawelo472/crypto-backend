package com.crypto.backend.login;

public interface UserService {
    String addUser(UserDTO userDTO);

    LoginMesage loginUser(LoginDTO loginDTO);

}
