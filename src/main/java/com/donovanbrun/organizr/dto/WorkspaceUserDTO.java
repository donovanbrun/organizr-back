package com.donovanbrun.organizr.dto;

import com.donovanbrun.organizr.Entity.WorkspaceRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class WorkspaceUserDTO {
    UserDTO user;
    WorkspaceRole role;
}
