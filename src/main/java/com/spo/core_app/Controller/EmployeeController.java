package com.spo.core_app.Controller;


import com.spo.core_app.DtoRequests.Empdto;
import com.spo.core_app.DtoResponses.LoginResponsedto;
import com.spo.core_app.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emp")
@Slf4j
public class EmployeeController {

    private AuthService authservice;
    @Autowired
    public EmployeeController(AuthService authservice){
        this.authservice=authservice;
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
}
