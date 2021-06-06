package com.oneToMany.api.controller;

import com.oneToMany.api.exception.DataNotFoundException;
import com.oneToMany.api.model.Role;
import com.oneToMany.api.model.User;
import com.oneToMany.api.service.RoleService;
import com.oneToMany.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostMapping("{id}/create/")
    public ResponseEntity<?> createUser(@PathVariable(value = "id") long id, @RequestBody Role role) throws DataNotFoundException {
        return userService.updateRoleByID(id, role);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListOfUsers() {
        return userService.readListOfUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable(value = "id") long id) throws DataNotFoundException {
        return userService.readUser(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") long id, @RequestBody User user) throws DataNotFoundException {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long id) {
        return userService.deleteUser(id);

    }

}
