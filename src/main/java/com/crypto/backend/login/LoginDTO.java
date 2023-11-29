package com.crypto.backend.login;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
