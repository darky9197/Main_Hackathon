package com.food.backend.service;

import com.food.backend.config.JwtUtil;
import com.food.backend.dto.AuthResponseDto;
import com.food.backend.dto.LoginDto;
import com.food.backend.dto.UserDto;
import com.food.backend.model.User;
import com.food.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRole() != null) user.setRole(userDto.getRole());
        
        userRepository.save(user);
        return true;
    }

    public AuthResponseDto loginUser(LoginDto loginReq) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
            );
        } catch (org.springframework.security.core.AuthenticationException e) {
            return null;
        }

        User user = userRepository.findByEmail(loginReq.getEmail());
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole());
        return new AuthResponseDto(token, user.getId(), user.getName(), user.getRole());
    }
}
