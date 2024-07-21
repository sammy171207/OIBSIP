package com.example.brs.controller;

import com.example.brs.dto.request.UpdateUserRequest;
import com.example.brs.modal.User;
import com.example.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findByUserByJwtToken(jwt);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(@RequestHeader("Authorization") String jwt, @RequestBody UpdateUserRequest userUpdates) {
        try {
            User updatedUser = userService.updateUser(jwt, userUpdates);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
