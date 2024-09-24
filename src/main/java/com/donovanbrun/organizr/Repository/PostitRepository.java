package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Postit;
import com.donovanbrun.organizr.Entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostitRepository extends JpaRepository<Postit, UUID> {

    List<Postit> getPostitByWorkspace(Workspace workspace);
}
