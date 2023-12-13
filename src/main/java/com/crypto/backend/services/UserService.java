package com.crypto.backend.services;
import com.crypto.backend.dtos.SignUpDto;
import com.crypto.backend.exceptions.AppException;
import com.crypto.backend.mappers.UserMapper;
import com.crypto.backend.dtos.CredentialsDto;
import com.crypto.backend.dtos.UserDto;
import com.crypto.backend.entites.User;
import com.crypto.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final PasswordEncoder apikeyEncoder;
    private final PasswordEncoder secretapikeyEncoder;
    private final UserMapper userMapper;
    @Value("${app.encryption.key}")
    private String encryptionKey;

    public byte[] encrypt(String data) {
       // System.out.println("Original data: " + data);

        try {
            BytesEncryptor encryptor = Encryptors.stronger(encryptionKey, encryptionKey);

            // Encode original data to bytes
            byte[] originalBytes = data.getBytes(StandardCharsets.UTF_8);
           //System.out.println("Original bytes: " + Arrays.toString(originalBytes));

            // Encrypt the bytes
            byte[] encryptedBytes = encryptor.encrypt(originalBytes);
           //System.out.println("Encrypted bytes: " + Arrays.toString(encryptedBytes));
            return encryptedBytes;
        } catch (Exception e) {
            // Print any exception that might occur during encryption
            e.printStackTrace();
            throw new RuntimeException("Encryption error", e);
        }
    }



    public String decrypt(byte[] encryptedBytes) {
        //System.out.println("Encrypted bytes: " + Arrays.toString(encryptedBytes));

        try {
            BytesEncryptor encryptor = Encryptors.stronger(encryptionKey, encryptionKey);

            byte[] decryptedBytes = encryptor.decrypt(encryptedBytes);
            //System.out.println("Decrypted bytes: " + Arrays.toString(decryptedBytes));

            // Convert the decrypted bytes to a String
            String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);

            // Return the decrypted String
            return decryptedString;
        } catch (Exception e) {
            // Print any exception that might occur during decryption
            e.printStackTrace();
            throw new RuntimeException("Decryption error", e);
        }
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return UserDto.builder()
                    .username(user.getUsername())
                    .apikey(decrypt(user.getApikey()))
                    .secretapikey(decrypt(user.getSecretapikey()))
                    .password(user.getPassword())
                    .id(user.getId())
                    .email(user.getEmail())
                    .build();
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUserByEmail = userRepository.findByEmail(userDto.getEmail());
        if (optionalUserByEmail.isPresent()) {
            throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
//        user.setApikey(apikeyEncoder.encode(CharBuffer.wrap(userDto.getApikey())));
//        user.setSecretapikey(secretapikeyEncoder.encode(CharBuffer.wrap(userDto.getSecretapikey())));

        user.setApikey(encrypt(userDto.getApikey()));
        user.setSecretapikey(encrypt(userDto.getSecretapikey()));

//        // Deszyfruj apikey i wypisz w konsoli
//        String decryptedApikey = decrypt(user.getApikey());
//        System.out.println("Decrypted Apikey: " + decryptedApikey);
//
//        // Deszyfruj secretapikey i wypisz w konsoli
//        String decryptedSecretApikey = decrypt(user.getSecretapikey());
//        System.out.println("Decrypted SecretApikey: " + decryptedSecretApikey);
            userRepository.save(user);

        return UserDto.builder()
                .username(user.getUsername())
                .apikey(decrypt(user.getApikey()))
                .secretapikey(decrypt(user.getSecretapikey()))
                .password(user.getPassword())
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}