package com.oneToMany.api.controller;

import com.oneToMany.api.exception.DataNotFoundException;
import com.oneToMany.api.model.Role;
import com.oneToMany.api.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/role")
@Tag(name = "role API", description = "Role API controller")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @PostMapping("/create")
    public ResponseEntity<?> createRoleWithUser(@RequestBody Role role) {
        return roleService.addRoleWithUser(role);
    }


    @Operation(summary = "Get list of all roles", tags = "{roles}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
                                        description = "Found the list of roles",
                                        content = {@Content (mediaType = "application/json",
                                                array = @ArraySchema(schema = @Schema(implementation = Role.class))
                                        ),})})
    @ApiResponse(responseCode = "400", description = "Invalid authorization",
            content = @Content)
    @GetMapping("/list")
    public ResponseEntity<?> getListOfRoles() {
        return roleService.readListOfRole();
    }

    @Operation(summary = "Get role by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Found Role by specific ID",
            content = {@Content (mediaType = "application/json",
                    schema = @Schema(implementation = Role.class)
            ),})})
    @ApiResponse(responseCode = "404", description = "ID not found in database", content = @Content)
    @GetMapping("/{id}")
    public Role getRoleByID(@PathVariable(value = "id") long id) throws DataNotFoundException {
        return roleService.readRole(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") long id) {
        return roleService.deleteRoleById(id);

    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(value = "id") long id, @RequestBody Role role) throws DataNotFoundException {
        return roleService.updateRoleByID(id, role);
    }

}
