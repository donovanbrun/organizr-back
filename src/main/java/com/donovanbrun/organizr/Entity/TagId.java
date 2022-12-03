package com.donovanbrun.organizr.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class TagId implements Serializable {

    protected String name;
    protected User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagId tagId = (TagId) o;
        return Objects.equals(name, tagId.name) && Objects.equals(user, tagId.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user);
    }
}