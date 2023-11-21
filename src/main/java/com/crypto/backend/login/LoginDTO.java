package com.crypto.backend.login;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class LoginDTO {
    private String email;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
