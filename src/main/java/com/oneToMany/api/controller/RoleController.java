package com.oneToMany.api.controller;

import com.oneToMany.api.exception.DataNotFoundException;
import com.oneToMany.api.model.Role;
import com.oneToMany.api.repository.RoleRepository;
import com.oneToMany.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @PostMapping("/create")
    public ResponseEntity<Object> createRoleWithUser(@RequestBody Role role) {
        return roleService.addRoleWithUser(role);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListOfRoles() {
        return roleService.readListOfRole();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleByID(@PathVariable(value = "id") long id) throws DataNotFoundException{
        return roleService.readRole(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") long id) {
        return roleService.deleteRoleById(id);

    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(value = "id")long id, @RequestBody Role role) throws DataNotFoundException {
        return roleService.updateRoleByID(id, role);
    }

}
