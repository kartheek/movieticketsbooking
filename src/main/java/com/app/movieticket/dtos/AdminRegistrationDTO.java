package com.app.movieticket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AdminRegistrationDTO {
    private String username;
    private String password;
}
