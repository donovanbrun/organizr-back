package com.donovanbrun.organizr.dto;

import com.donovanbrun.organizr.Entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private String username;
    private String email;
    private UserRole role;
}
