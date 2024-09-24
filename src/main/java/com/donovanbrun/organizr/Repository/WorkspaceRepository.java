package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.User;
import com.donovanbrun.organizr.Entity.Workspace;
import com.donovanbrun.organizr.Entity.WorkspaceRole;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    @Query(value = """
        select w
        from Workspace w
        inner join WorkspaceUser wu
        on w = wu.workspace
        where wu.user = :user
    """)
    List<Workspace> getWorkspacesByUser(User user);

    @Query("""
        select wu.role
        from WorkspaceUser wu
        where wu.workspace = :workspace
        and wu.user = :user
    """)
    WorkspaceRole getRole(User user, Workspace workspace);

    @Query("""
        select wu.user, wu.role
        from WorkspaceUser wu
        where wu.workspace = :workspace
    """)
    List<Tuple> getUsersByWorkspace(Workspace workspace);
}
