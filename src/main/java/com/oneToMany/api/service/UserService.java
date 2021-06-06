package com.oneToMany.api.service;

import com.oneToMany.api.exception.DataNotFoundException;
import com.oneToMany.api.model.Role;
import com.oneToMany.api.model.User;
import com.oneToMany.api.repository.RoleRepository;
import com.oneToMany.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;


    //create user by role id
    public ResponseEntity<?> addUser(long roleID, User user) throws DataNotFoundException {
        if (roleRepository.findById(roleID).isPresent()) {
            return ResponseEntity.ok().body(userRepository.save(user));
        } else {
            return ResponseEntity.unprocessableEntity().body(new DataNotFoundException("Role ID not found: " + roleID));
        }
    }

    public ResponseEntity<?> updateRoleByID(long id, Role role) throws DataNotFoundException {
        Role newRole = roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Role ID not found in Database: " + id));
        newRole.setUsers(role.getUsers());
        Role updatedRole = roleRepository.save(newRole);
        return ResponseEntity.ok(updatedRole);
    }


    //Read list of all users
    public ResponseEntity<?> readListOfUser() {
        List<User> user = userRepository.findAll();
        return ResponseEntity.ok().body(user);
    }


    //Read the user and user details
    public ResponseEntity<?> readUser(long id) throws DataNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Given ID not found in database: " + id));
        return ResponseEntity.ok(user);
    }

    //Update existing user
    public ResponseEntity<?> updateUser(long id, User user) throws DataNotFoundException {
        User newUser = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User ID not found in Database: " + id));
        newUser.setUserName(user.getUserName());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User updatedUser = userRepository.save(newUser);
        return ResponseEntity.ok(updatedUser);
    }


    //Delete User
    public ResponseEntity<?> deleteUser(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            if (userRepository.findById(id).isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Failed to delete the given id: " + id);
            } else return ResponseEntity.ok().body("Successfully deleted the given id: " + id);
        } else
            return ResponseEntity.unprocessableEntity().body("No record found");
    }

}
