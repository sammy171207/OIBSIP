package com.example.brs.service;

import com.example.brs.modal.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    public User saveUser(User user) throws Exception;


    public User findByUsername(String username) throws Exception;

    public UserDetails loadUserByUsername(String username)throws Exception;
    public User findByUserByJwtToken(String jwt) throws Exception;
}
