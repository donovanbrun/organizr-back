package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Service.PostitService;
import com.donovanbrun.organizr.dto.PostitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/postit")
@RequiredArgsConstructor
public class PostitController {

    private final PostitService postitService;

    @GetMapping
    public ResponseEntity getPostit(@AuthenticationPrincipal User user, @RequestParam("workspace") UUID workspaceId) {
        try {
            return ResponseEntity.ok(postitService.getPostit(user, workspaceId));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity createPostit(@AuthenticationPrincipal User user, @RequestBody PostitDTO postitDTO) {
        try {
            return ResponseEntity.ok(postitService.create(user, postitDTO));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updatePostit(@AuthenticationPrincipal User user, @RequestBody PostitDTO postitDTO) {
        try {
            return ResponseEntity.ok(postitService.update(user, postitDTO));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostit(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        try {
            postitService.delete(id, user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
