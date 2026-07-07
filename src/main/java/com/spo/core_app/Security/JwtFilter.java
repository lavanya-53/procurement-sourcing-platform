package com.spo.core_app.Security;

import com.spo.core_app.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException;
import java.util.Collections;

//now this class will act hjas jwtfilter class after extending that class which comes from spring security dependen
@Component
public class JwtFilter extends OncePerRequestFilter {


    private com.spo.core_app.utilities.JWTutility jwtutility;
    @Autowired
    public JwtFilter(com.spo.core_app.utilities.JWTutility jwtutility){
        this.jwtutility=jwtutility;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //"Go inside the incoming request and give me the value stored under the header name token."
        //There are many headers.
        //
        //How does Java know which one you want?
        //
        //You tell it:
        String token=request.getHeader(token);
        if(token==null||token.isEmpty()){
            //withpit authentication u r calling do filter it will reject the request // If you will call doFilter without setting some kind of auth you will reject the request
            filterChain.doFilter(request,response);
            return;
        }
        //if the token is not empty trim the extra spaces
        token=token.trim();
        //token is not empty we should validate the token
        //Take the token, verify it, and return the user corresponding to that token."
        //Examples:
        //
        //Token is expired
        //Token signature is wrong
        //User was deleted from DB
        User user=jwtutility.ValidateToken(token);
        //I could not authenticate any user, so I am not setting any logged-in user information. Just continue the request processing."
        if(user==null){
            filterChain.doFilter(request,response);
            return;
        }
        //at this time only jwt filter knows logged in user but not the spring security so to get to know to spring security we use this class it is spring security class
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        //et current logged-in user anywhere
        //SecurityContextHolder makes the authenticated user available to the entire Spring Security framework during that request.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
