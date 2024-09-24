package com.donovanbrun.organizr.Entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "WorkspaceUser",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"user_id", "workspace_id"})
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkspaceUser {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private WorkspaceRole role;
}
