package com.spo.core_app.Controller;


import com.spo.core_app.DtoRequests.CreateRoleRequest;
import com.spo.core_app.Exceptions.UNAuthorizedException;
import com.spo.core_app.Service.AuthService;
import com.spo.core_app.Service.RoleService;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/api/v1/Role")
public class RoleController {

    private AuthService authservice;
    private RoleService roleservice;
    @Autowired
    public RoleController(AuthService authservice,RoleService roleservice){
        this.authservice=authservice;
        this.roleservice=roleservice;
    }
    @PostMapping("/Create-Role")
    public ResponseEntity CreateRole(@RequestBody CreateRoleRequest createrolerequest,
                                     @RequestHeader String token){
        // We are going to verify that user is having the role which contains CREATE_ROLE
        try {
            Employee emp=authservice.IsUserAllowedToPerformOperation(token, "CREATE_ROLE");
            Roles role=roleservice.CreateRole(createrolerequest,emp);
            return new ResponseEntity(role, HttpStatus.OK);

    }catch (UNAuthorizedException e){
        HashMap<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", e.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.UNAUTHORIZED);
    }catch (Exception e){
        HashMap<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", e.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
}
