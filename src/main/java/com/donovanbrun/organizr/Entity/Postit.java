package com.donovanbrun.organizr.Entity;

import com.donovanbrun.organizr.dto.PostitDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Postit")
public class Postit {

    @Id
    private UUID id;
    @ManyToOne
    private User user;
    private String content;

    public Postit(PostitDTO postitDTO, User user) {
        this.id = postitDTO.getId();
        this.user = user;
        this.content = postitDTO.getContent();
    }
}
