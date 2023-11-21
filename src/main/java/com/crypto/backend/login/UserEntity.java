package com.crypto.backend.login;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table
@Entity
public class UserEntity {
    @Id
    @Column(name="user_id", length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;
    @Column(name="user_name")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="api_key")
    private Long apikey;
    @Column(name="user_email")
    private String email;

    public UserEntity(int userid, String username, String password, String email, Long apikey) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.apikey = apikey;
        this.email = email;
    }

    public UserEntity() {

    }
}
