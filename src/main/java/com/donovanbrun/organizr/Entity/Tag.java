package com.donovanbrun.organizr.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    private User user;
}
