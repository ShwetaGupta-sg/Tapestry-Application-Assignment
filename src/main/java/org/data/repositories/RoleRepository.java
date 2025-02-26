package org.data.repositories;

import org.data.entities.Role;

public interface RoleRepository {

    Role findByName(String roleName);
    Role getRoleById(Long id);
}
