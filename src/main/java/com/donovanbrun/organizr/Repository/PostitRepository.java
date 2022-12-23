package com.donovanbrun.organizr.Repository;

import com.donovanbrun.organizr.Entity.Postit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.donovanbrun.organizr.Entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostitRepository extends CrudRepository<Postit, UUID> {

    List<Postit> getPostitByUser(User user);
}
