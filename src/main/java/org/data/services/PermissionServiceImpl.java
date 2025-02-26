package org.data.services;

import org.data.entities.Employee;
import org.data.repositories.PermissionRepository;
import org.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Set<String> getAllPermissions() {
        return Set.of("VIEW_EMPLOYEES", "EDIT_EMPLOYEES", "DELETE_EMPLOYEES", "ADD_EMPLOYEES");
    }
    @Override
    public boolean hasPermission(Employee employee, String permissionName) {
        if (employee == null || employee.getPermissions() == null) {
            return false;
        }

        // Admin should have all permissions by default
        if ("ADMIN".equalsIgnoreCase(employee.getRole().getName())) {
            return true;
        }

        // Check if the employee has the specific permission
        return employee.getPermissions().stream()
                .anyMatch(permission -> permission.getName().equalsIgnoreCase(permissionName));
    }
    @Override
    public boolean hasEditPermission(Long employeeId) {
        return permissionRepository.hasEditPermission(employeeId);
    }

}
