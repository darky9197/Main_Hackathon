package com.backend.backend.service;

import com.backend.backend.model.Users;
import com.backend.backend.repository.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public MyUserDetailsService(UserRepository repository){
        this.repository = repository;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users myUser = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles(extractRoles(myUser))
                .build();
    }

    private String[] extractRoles(Users user) {
        if(user == null){
            return new String[]{"EMPLOYEE"};
        }
        return user.getRoles().split(",");
    }
}
