package com.crypto.backend.login;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class UserDTO {

    private int userid;
    private String username;
    private String email;
    private String password;
    private Long apikey;

    public UserDTO() {
    }

    public UserDTO(int userid, String username, String email, String password, Long apikey) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.apikey = apikey;
    }
}
