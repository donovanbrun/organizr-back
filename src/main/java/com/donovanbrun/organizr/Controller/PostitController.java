package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Service.PostitService;
import com.donovanbrun.organizr.dto.PostitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/postit")
public class PostitController {

    private PostitService postitService;

    @Autowired
    public PostitController(PostitService postitService) {
        this.postitService = postitService;
    }

    @GetMapping
    public List<PostitDTO> getPostit(@RequestHeader UUID userID) {
        return postitService.getPostit(userID);
    }

    @PostMapping("create")
    public void createPostit(@RequestBody PostitDTO postitDTO) {
        postitService.create(postitDTO);
    }

    @PutMapping("update")
    public void updatePostit(@RequestBody PostitDTO postitDTO) {
        postitService.update(postitDTO);
    }

    @DeleteMapping("delete/{id}")
    public void deletePostit(@PathVariable UUID id, @RequestHeader UUID userID) {
        postitService.delete(id, userID);
    }
}
