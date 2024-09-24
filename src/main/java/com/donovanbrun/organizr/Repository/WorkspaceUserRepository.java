package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, UUID> {
}
