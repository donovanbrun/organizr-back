package com.donovanbrun.organizr.Entity;

import com.donovanbrun.organizr.dto.PostitDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Postit")
public class Postit {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Workspace workspace;

    private String content;
    private Date creationDate;

    public Postit(PostitDTO postitDTO, User user, Workspace workspace) {
        this.id = postitDTO.getId();
        this.user = user;
        this.workspace = workspace;
        this.content = postitDTO.getContent();
        this.creationDate = postitDTO.getCreationDate();
    }
}
