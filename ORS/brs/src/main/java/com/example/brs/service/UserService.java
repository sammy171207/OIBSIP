package com.example.brs.service;

import com.example.brs.dto.request.UpdateUserRequest;
import com.example.brs.modal.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public User saveUser(User user) throws Exception;


    public User findByUsername(String username) throws Exception;

    public UserDetails loadUserByUsername(String username)throws Exception;
    public User findByUserByJwtToken(String jwt) throws Exception;
    List<User> getAllUsers() throws Exception;
    User updateUser(String jwt, UpdateUserRequest updateRequest) throws Exception;
    void deleteUser(Long id) throws Exception;

}
