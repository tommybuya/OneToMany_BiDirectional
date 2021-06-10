package com.oneToMany.api.service;

import com.oneToMany.api.exception.DataNotFoundException;
import com.oneToMany.api.model.Role;
import com.oneToMany.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    //new Role with new Users
    @Transactional
    public ResponseEntity<Object> addRoleWithUser(Role role) {
        Role newRole = new Role();
        newRole.setRoleName(role.getRoleName());
        newRole.setDescription(role.getDescription());
        newRole.setUsers(role.getUsers());

        Role saveRole = roleRepository.save(newRole);
        if (roleRepository.findById(saveRole.getId()).isPresent()) {
            return ResponseEntity.accepted().body("Successfully created Role with Users");
        } else {
            return ResponseEntity.unprocessableEntity().body("Failed to create Role with Users");
        }
    }


    //Delete Role
    public ResponseEntity<?> deleteRoleById(long id) {
        if (roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
            if (roleRepository.findById(id).isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Failed to delete the given id: " + id);
            } else return ResponseEntity.ok().body("Successfully deleted the given id: " + id);
        } else
            return ResponseEntity.unprocessableEntity().body("No record found");
    }


    //Read the role and user details
    public Role readRole(long id) throws DataNotFoundException {
        Role role = roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Given ID not found in database: " + id));
        return role;
    }


    //Read list of all roles
    public ResponseEntity<?> readListOfRole() {
        List<Role> role = roleRepository.findAll();
        return ResponseEntity.ok().body(role);
    }


    //Update existing role
    public ResponseEntity<?> updateRoleByID(long id, Role role) throws DataNotFoundException {
        Role newRole = roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Role ID not found in Database: " + id));
        newRole.setRoleName(role.getRoleName());
        newRole.setDescription(role.getDescription());
        newRole.setUsers(role.getUsers());

        Role updatedRole = roleRepository.save(newRole);
        return ResponseEntity.ok(updatedRole);
    }
}// close RoleRepository class