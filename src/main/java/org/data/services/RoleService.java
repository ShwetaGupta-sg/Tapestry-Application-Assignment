package org.data.services;

import org.data.entities.Role;

import java.util.List;

public interface RoleService {
    List<String> getAllRoles();  // Fetch role names for dropdown
    Role findByName(String roleName);  // Get role entity by name
}