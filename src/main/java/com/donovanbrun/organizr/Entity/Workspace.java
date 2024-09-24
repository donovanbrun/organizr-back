package com.donovanbrun.organizr.Entity;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Workspace")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Workspace {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
