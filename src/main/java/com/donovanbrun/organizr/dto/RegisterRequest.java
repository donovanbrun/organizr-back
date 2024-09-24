package com.donovanbrun.organizr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
