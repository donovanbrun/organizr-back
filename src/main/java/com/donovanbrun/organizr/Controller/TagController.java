package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.Tag;
import com.donovanbrun.organizr.Repository.TagRepository;
import com.donovanbrun.organizr.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<String> getAll(@RequestHeader UUID userId) {
        return tagRepository.findAllByUser(userService.getUserById(userId)).stream().map(Tag::getName).toList();
    }
}
