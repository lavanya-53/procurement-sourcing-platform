package com.spo.core_app.Service;

import com.spo.core_app.DtoRequests.Empdto;
import com.spo.core_app.DtoResponses.LoginResponsedto;
import com.spo.core_app.Exceptions.UNAuthorizedException;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.Operation;
import com.spo.core_app.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class AuthService {
    private EmployeeService empservice;
    private com.spo.core_app.utilities.JWTutility jwtutility;
    @Autowired
    public AuthService(EmployeeService empservice, com.spo.core_app.utilities.JWTutility jwtiutility) {
        this.empservice = empservice;
        this.jwtutility=jwtutility;

    }
    public LoginResponsedto AuthenticateEmployee(Empdto logindto){
                String email=logindto.getEmail();
                String password=logindto.getPassword();
                Employee emp=empservice.ValidateEmployeeCredentials(email,password);
               String Token= jwtutility.generateJwtToken(emp);
               return LoginResponsedto.builder().Token(Token).LoginTime(LocalDateTime.now()).build();
    }
    public Employee IsUserAllowedToPerformOperation(String token,String operation){
        Employee emp=jwtutility.ValidateToken(token);
        List<Roles> role=emp.getRole();
        for(Roles r:role){
            for(Operation op:r.getOperations()){
                if(op.getOperationName().equals(operation)){
                    return emp;
                }
            }
        }
        //No Role is containing this operation then throw error
        throw new UNAuthorizedException("User is not allowed to perform this operation");
    }
}
