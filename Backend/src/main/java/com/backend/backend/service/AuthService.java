package com.backend.backend.service;

import com.backend.backend.configuration.JwtUtil;
import com.backend.backend.dto.AuthResponseDto;
import com.backend.backend.dto.LoginDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.model.Users;
import com.backend.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public boolean registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) return false;

        Users user = new Users();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRole() != null) user.setRole(userDto.getRole());

        userRepository.save(user);
        return true;
    }

    public AuthResponseDto loginUser(LoginDto loginReq) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReq.getEmail(),
                        loginReq.getPassword()
                )
        );

        Users user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getUserId(),
                user.getRole()
        );

        return new AuthResponseDto(
                token,
                user.getUserId(),
                user.getName(),
                user.getRole()
        );
    }
}
