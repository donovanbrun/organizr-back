package com.donovanbrun.organizr.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(TagId.class)
public class Tag {

    @Id
    private String name;
    @Id
    @ManyToOne
    private User user;
}
