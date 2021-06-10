package com.oneToMany.api;

import com.oneToMany.api.controller.RoleController;
import com.oneToMany.api.exception.DataNotFoundException;
import com.oneToMany.api.model.Role;
import com.oneToMany.api.model.User;
import com.oneToMany.api.service.RoleService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
class OneToManyApplicationTests {
    String RoleAPIURL = "http://localhost:4200/api/role";
    Response response;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;


    @Test()
    @DisplayName("Get role by id using MockMVC")
    public void testGetWithMockMvc() throws DataNotFoundException {
        List<User> userList = new ArrayList<>();
        Role role = new Role (56L, "MOCKITO_ADMIN", "Mockito role name with twenty five characters", userList);
        Mockito.when(roleService.readRole(56)).thenReturn(role);

        Role newRole = roleController.getRoleByID(56);
        assertEquals(role,newRole);
        System.out.println(role);
        System.out.println(newRole);

    }

    @Test()
    @DisplayName("Get list of roles")
    public void testRoleList(){
        response = given()
                .auth()
                .preemptive()
                .basic("admin", "admin123")
                .param("Content-Type", "application.json")
                .when().get(RoleAPIURL + "/list");
        response.then().assertThat().body("roleName[0]", is("Admin"));
        response.then().assertThat().body("roleName[1]", is("Sales"));
        response.then().assertThat().body("roleName[2]", is("ADMIN"));
    }

    @Test()
    @DisplayName("Get Role by ID")
    public void testRoleByID(){
        response = given()
                .auth()
                .preemptive()
                .basic("admin", "admin123")
                .param("Content-Type", "application.json")
                .when().get(RoleAPIURL + "/4");
        response.then().assertThat().body("roleName", is("Admin"));
    }

    @Test
    @DisplayName("Get Role List without Authentication")
    public void testRoleListWithoutAuth(){
        int statusCode = given()
                .param("Content-Type", "application.json")
                .when().get(RoleAPIURL + "/list")
                .thenReturn().getStatusCode();
        assertEquals(statusCode, 401);
    }


    @Test
    @DisplayName("Create new Role")
    public void testCreateMewRole(){
        response = given()
                .auth()
                .preemptive()
                .basic("admin", "admin123")
                .param("Content-Type", "application.json")
                .and()
                .body("")
                .when().post(RoleAPIURL + "/create");
        response.prettyPrint();
    }

    @Test
    @DisplayName("Create new Role with valid request")
    public void testValidRequest() throws JSONException {
        JSONArray user = new JSONArray();

        JSONObject role = new JSONObject();
        role.put("roleName", "TEST_ADMIN");
        role.put("description", "Test User Description for the role");
        role.put("users:", user);


        response = (Response) given()
                .auth()
                .preemptive()
                .basic("admin", "admin123")
                .contentType(ContentType.JSON)
                .param("Content-Type", "application.json")
                .body(role)
                .when().post(RoleAPIURL + "/create")
                .then()
                .log().body();
        System.out.println(response.getStatusCode());
    }


}
