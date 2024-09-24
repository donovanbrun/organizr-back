package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Entity.Workspace;
import com.donovanbrun.organizr.Entity.WorkspaceRole;
import com.donovanbrun.organizr.Entity.WorkspaceUser;
import com.donovanbrun.organizr.Repository.WorkspaceRepository;
import com.donovanbrun.organizr.Repository.WorkspaceUserRepository;
import com.donovanbrun.organizr.dto.UserDTO;
import com.donovanbrun.organizr.dto.WorkspaceDTO;
import com.donovanbrun.organizr.dto.WorkspaceUserDTO;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final UserService userService;

    @Transactional
    public Workspace create(WorkspaceDTO workspaceDTO, User user) {
        Workspace workspace = Workspace.builder()
                .name(workspaceDTO.getName())
                .build();

        WorkspaceUser workspaceUser = WorkspaceUser.builder()
                .role(WorkspaceRole.OWNER)
                .workspace(workspace)
                .user(user)
                .build();

        workspace = workspaceRepository.save(workspace);
        workspaceUserRepository.save(workspaceUser);
        return workspace;
    }

    public List<Workspace> getByUser(User user) {
        return workspaceRepository.getWorkspacesByUser(user);
    }

    public Optional<Workspace> getWorkspace(UUID workspaceId) {
        return workspaceRepository.findById(workspaceId);
    }

    public boolean canView(User user, Workspace workspace) {
        return workspaceRepository.getRole(user, workspace) != null;
    }

    public boolean canEdit(User user, Workspace workspace) {
        return List.of(WorkspaceRole.MEMBER, WorkspaceRole.OWNER).contains(workspaceRepository.getRole(user, workspace));
    }

    public boolean canManage(User user, Workspace workspace) {
        return Objects.equals(WorkspaceRole.OWNER, workspaceRepository.getRole(user, workspace));
    }

    public void addUser(User user, UUID workspaceId, String email, String role) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow();

        if (!canManage(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot manage this workspace");
        }

        User userToAdd = userService.getUserByEmail(email)
                .orElseThrow();

        WorkspaceUser workspaceUser = WorkspaceUser.builder()
                .role(WorkspaceRole.valueOf(role))
                .workspace(workspace)
                .user(userToAdd)
                .build();

        workspaceUserRepository.save(workspaceUser);
    }

    public List<WorkspaceUserDTO> getUsers(User user, UUID workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow();

        if (!canView(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot manage this workspace");
        }

        List<Tuple> users = workspaceRepository.getUsersByWorkspace(workspace);
        return users.stream().map(
                t -> {
                    User u = (User) t.get(0);
                    WorkspaceRole wr = (WorkspaceRole) t.get(1);
                    return WorkspaceUserDTO.builder()
                            .user(
                                    UserDTO.builder()
                                            .email(u.getEmail())
                                            .username(u.getUsername())
                                            .role(u.getRole())
                                            .build()
                            )
                            .role(wr)
                            .build();
                }
        ).toList();
    }
}
