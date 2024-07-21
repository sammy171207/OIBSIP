package com.example.brs.service.impl;

import com.example.brs.config.JwtProvider;
import com.example.brs.dto.request.UpdateUserRequest;
import com.example.brs.modal.ROLE;
import com.example.brs.modal.User;
import com.example.brs.repository.UserRepository;
import com.example.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) throws Exception {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws Exception {
        User user=findByUsername(username);
        System.out.println(username+"user");
        if(user ==null){
            throw new UsernameNotFoundException("User not found with username"+username);
        }
        ROLE role =user.getRole();
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
    }

    @Override
    public User findByUserByJwtToken(String jwt) throws Exception {
        String username=jwtProvider.getUsernameFromJwtToken(jwt);
        System.out.println(username);
        User user=findByUsername(username);
        if(user==null){
            throw  new Exception("not found");
        }
        return user;

    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userRepository.findAll();
    }


    @Override
    public User updateUser(String jwt, UpdateUserRequest updateRequest) throws Exception {
        String username = jwtProvider.getUsernameFromJwtToken(jwt);
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser == null) {
            throw new Exception("User not found");
        }

        // Update fields based on the request
        if (updateRequest.getUsername() != null) {
            existingUser.setUsername(updateRequest.getUsername());
        }
        if (updateRequest.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        // Save the updated user
        return userRepository.save(existingUser);

    }

    @Override
    public void deleteUser(Long id) throws Exception {
        if (!userRepository.existsById(id)) {
            throw new Exception("User not found");
        }
        userRepository.deleteById(id);
    }
}
