package com.crypto.backend.entites;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Blob;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EntityScan
@Table(name = "app_user")
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_name", nullable = false)
    private String username;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="api_key", nullable = false)
    private byte[] apikey;
    @Column(name="user_email", nullable = false)
    private String email;
    @Column(name="secret_api_key", nullable = false)
    private byte[] secretapikey;



}
