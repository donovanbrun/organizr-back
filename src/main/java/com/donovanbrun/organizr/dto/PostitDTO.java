package com.donovanbrun.organizr.dto;

import com.donovanbrun.organizr.Entity.Postit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostitDTO {

    private UUID id;
    private UUID userId;
    private String content;
    private Date creationDate;

    public PostitDTO(Postit postit) {
        this.id = postit.getId();
        this.userId = postit.getUser().getId();
        this.content = postit.getContent();
        this.creationDate = postit.getCreationDate();
    }
}
