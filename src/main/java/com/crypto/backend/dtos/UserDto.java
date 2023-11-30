package com.crypto.backend.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {

    private int id;
    private String email;
    private String password;
    private String apikey;
    private String username;
    private String token;


}
