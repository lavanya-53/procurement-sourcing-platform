package com.spo.core_app.Service;

import com.spo.core_app.DtoRequests.Empdto;
import com.spo.core_app.DtoResponses.LoginResponsedto;
import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.Enums.UserType;
import com.spo.core_app.Exceptions.InvalidCredentialException;
import com.spo.core_app.Exceptions.UNAuthorizedException;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.Operation;
import com.spo.core_app.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AuthService {
    private EmployeeService empservice;
    private com.spo.core_app.Utilities.JWTutility jwtutility;
    @Autowired
    public AuthService(EmployeeService empservice, com.spo.core_app.Utilities.JWTutility jwtutility) {
        this.empservice = empservice;
        this.jwtutility=jwtutility;

    }
    public LoginResponsedto AuthenticateEmployee(Empdto logindto){
                String email=logindto.getEmail();
                String password=logindto.getPassword();
                Employee emp=empservice.ValidateEmployeeCredentials(email,password);
        if (emp.getUserType() == UserType.Supplier_user) {

            if (emp.getCompany().getCompanyStatus() != CompanyStatus.ACTIVE) {

                throw new InvalidCredentialException(
                        "Your supplier account is waiting for approval.");
            }
        }
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
