package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Entity.Workspace;
import com.donovanbrun.organizr.Entity.WorkspaceUser;
import com.donovanbrun.organizr.Service.WorkspaceService;
import com.donovanbrun.organizr.dto.WorkspaceDTO;
import com.donovanbrun.organizr.dto.WorkspaceUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<List<Workspace>> getByUser(@AuthenticationPrincipal User user) {
        List<Workspace> workspaces = workspaceService.getByUser(user);
        return ResponseEntity.ok(workspaces);
    }

    @PostMapping
    public ResponseEntity<Workspace> create(@AuthenticationPrincipal User user, @RequestBody WorkspaceDTO workspaceDTO) {
        Workspace w = workspaceService.create(workspaceDTO, user);
        return ResponseEntity.ok(w);
    }

    @PostMapping("/adduser")
    public ResponseEntity addUser(@AuthenticationPrincipal User user, @RequestParam UUID workspaceId, @RequestParam String email, @RequestParam String role) {
        try {
            workspaceService.addUser(user, workspaceId, email, role);
            return ResponseEntity.accepted().build();
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity getUsers(@AuthenticationPrincipal User user, @RequestParam UUID workspace) {
        try {
            List<WorkspaceUserDTO> users = workspaceService.getUsers(user, workspace);
            return ResponseEntity.ok(users);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
