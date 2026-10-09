package com.spo.core_app.Controller;


import com.spo.core_app.DtoRequests.Empdto;
import com.spo.core_app.DtoRequests.InviteEmployeeRequest;
import com.spo.core_app.DtoResponses.LoginResponsedto;
import com.spo.core_app.Exceptions.UNAuthorizedException;
import com.spo.core_app.Service.AuthService;
import com.spo.core_app.Service.EmployeeService;
import com.spo.core_app.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/emp")
@Slf4j
public class EmployeeController {

    private AuthService authservice;
    private EmployeeService empservice;
    @Autowired
    public EmployeeController(AuthService authservice,EmployeeService empservice){
        this.authservice=authservice;
        this.empservice=empservice;
    }
    @PostMapping("/login")
    //Take the JSON coming inside the HTTP request body and convert it into a Java object.-Request Body
    public ResponseEntity EmpLogin(@RequestBody Empdto logindto){
        LoginResponsedto loginresponsedto=authservice.AuthenticateEmployee(logindto);
        return new ResponseEntity(loginresponsedto, HttpStatus.OK);
        //We use builder() when WE are creating a new object ourselves.
        //
        //We don't use builder() when Spring has already created the object for us.
    }
    @PostMapping("/Invite-User")
    public ResponseEntity inviteuser(@RequestBody InviteEmployeeRequest inviteemployeeRequest,@RequestHeader String token)
    {
             try{
                 Employee invitor=authservice.IsUserAllowedToPerformOperation(token,"INVITE_USER");
                 Employee invitee=empservice.inviteEmployee(inviteemployeeRequest,invitor);
                 return new ResponseEntity<>(invitee,HttpStatus.CREATED);
             }
             catch (
    UNAuthorizedException e){
        HashMap<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", e.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.UNAUTHORIZED);
    }
    catch (Exception e){
        HashMap<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", e.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
}
