package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Postit;
import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Repository.PostitRepository;
import com.donovanbrun.organizr.dto.PostitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class PostitService {

    private PostitRepository postitRepository;
    private UserService userService;

    @Autowired
    public PostitService(PostitRepository postitRepository, UserService userService) {
        this.postitRepository = postitRepository;
        this.userService = userService;
    }

    public List<PostitDTO> getPostit(UUID userId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            return postitRepository.getPostitByUser(u).stream().map(PostitDTO::new).toList();
        }
        else throw new RuntimeException();
    }

    public void create(PostitDTO postitDTO) {
        User u = userService.getUserById(postitDTO.getUserId());
        if (u != null) {
            Postit postit = new Postit(postitDTO, u);
            postitRepository.save(postit);
        }
        else throw new RuntimeException();
    }

    public void update(PostitDTO postitDTO) {
        User u = userService.getUserById(postitDTO.getUserId());
        if (u != null) {
            Postit postit = new Postit(postitDTO, u);
            postitRepository.save(postit);
        }
        else throw new RuntimeException();
    }

    public void delete(UUID id, UUID userId) {
        User u = userService.getUserById(userId);
        if (u != null) {
            postitRepository.deleteById(id);
        }
        else throw new RuntimeException();
    }
}
