package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Postit;
import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Entity.Workspace;
import com.donovanbrun.organizr.Repository.PostitRepository;
import com.donovanbrun.organizr.dto.PostitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostitService {

    private final PostitRepository postitRepository;
    private final UserService userService;
    private final WorkspaceService workspaceService;

    public List<PostitDTO> getPostit(User user, UUID workspaceId) {
        Workspace workspace = workspaceService.getWorkspace(workspaceId)
                .orElseThrow();

        if (!workspaceService.canView(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot view this workspace");
        }

        return postitRepository.getPostitByWorkspace(workspace).stream().map(PostitDTO::new).toList();
    }

    public PostitDTO create(User user, PostitDTO postitDTO) {
        Workspace workspace = workspaceService.getWorkspace(postitDTO.getWorkspaceId())
                .orElseThrow();

        if (!workspaceService.canEdit(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot edit this workspace");
        }

        Postit postit = Postit.builder()
                .user(user)
                .workspace(workspace)
                .content(postitDTO.getContent())
                .creationDate(new Date())
                .build();

        return new PostitDTO(
            postitRepository.save(postit)
        );
    }

    public PostitDTO update(User user, PostitDTO postitDTO) {
        Workspace workspace = workspaceService.getWorkspace(postitDTO.getWorkspaceId())
                .orElseThrow();

        if (!workspaceService.canEdit(user, workspace)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot edit this workspace");
        }

        Postit postit = postitRepository.findById(postitDTO.getId())
                .orElseThrow();

        postit.setContent(postitDTO.getContent());

        return new PostitDTO(
                postitRepository.save(postit)
        );
    }

    public void delete(UUID id, User user) {
        Postit postit = postitRepository.findById(id)
                .orElseThrow();

        if (!workspaceService.canEdit(user, postit.getWorkspace())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Cannot edit this workspace");
        }

        postitRepository.delete(postit);
    }
}
