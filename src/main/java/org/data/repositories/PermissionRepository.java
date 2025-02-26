package org.data.repositories;

import org.data.entities.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository {
    List<Permission> getAllPermissions();
    Permission getPermissionByName(String name);

    boolean hasEditPermission(Long employeeId);
}

