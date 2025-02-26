package org.data.services;

import org.data.entities.Employee;

import java.util.Set;

public interface PermissionService {
    boolean hasPermission(Employee employee, String permissionName);
    Set<String> getAllPermissions();

    boolean hasEditPermission(Long id);

}
