package com.backend.backend.service;

import com.backend.backend.model.LoginDTO;
import com.backend.backend.model.Users;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public @Nullable String register(Users user) {
    }

    public @Nullable String verify(LoginDTO user) {
        return null;
    }
}
