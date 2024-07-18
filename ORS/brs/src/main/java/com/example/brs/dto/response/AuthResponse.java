package com.example.brs.dto.response;

import com.example.brs.modal.ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private ROLE role;
    private  String message;
    private  String username;
}
