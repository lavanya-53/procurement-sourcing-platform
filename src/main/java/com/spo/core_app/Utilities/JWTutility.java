package com.spo.core_app.utilities;

import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.Service.EmployeeService;
import com.spo.core_app.constants.SystemConstant;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.Role;
import com.spo.core_app.models.Roles;
import com.spo.core_app.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JWTutility {

 private EmployeeService empservice;
 @Autowired
    public JWTutility(EmployeeService empservice){
     this.empservice=empservice;
 }
    private final Key key = Keys.hmacShaKeyFor(SystemConstants.JWT_SECRET_PASSWORD.getBytes());


    public String generateJwtToken(Employee employee){

        // Claims -> User Email & Roles
        // Email -> Subject & Roles is the claim
        List<Roles> roles = employee.getRole();
        List<String> roleNames = new ArrayList<>();
        for(Roles role: roles){
            roleNames.add(role.getRoleName());
        }

        return Jwts.builder()
                .setSubject(employee.getEmail())
                .claim("roles", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SystemConstants.JWT_TOKEN_EXPIRATION_TIME))
                .signWith(key)
                .compact();//create teh jwt token and return it has string

    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractSubjectFromJwt(String token){
        Claims claims = this.extractAllClaims(token);
        return claims.getSubject();
    }
    public Employee ValidateToken(String Token){
        //decrypt token
        String Subject=this.extractSubjectFromJwt(Token);
        return empservice.getEmployeebyemail(Subject);
    }



}
