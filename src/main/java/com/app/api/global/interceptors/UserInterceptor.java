package com.app.api.global.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.app.api.enums.AccessEnum;
import com.app.api.enums.TokenEnum;
import com.app.api.global.UserInfo;
import com.app.api.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserInterceptor{
    @Autowired
    private JwtUtil jwtUtil;
    
    public boolean verify(HttpServletRequest request) {
        String headerValue = request.getHeader("Authorization");
        if(headerValue == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Claims claims = this.jwtUtil.verifyToken(headerValue, TokenEnum.ACCESS_TOKEN);
        Integer type = (Integer) claims.get("access");
        if(type != AccessEnum.USER.ordinal()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        request.setAttribute(
            "user", 
            UserInfo
                .builder()
                .id((int)claims.get("id"))
                .unique((String)claims.get("unique"))
                .access(type)
                .build()
        );
        return true;
    }
}
