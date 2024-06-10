package com.app.api.global.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.app.api.global.annotations.AdminRole;
import com.app.api.global.annotations.UserRole;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Interceptors implements HandlerInterceptor{

    @Autowired
    private AdminInterceptor authInterceptor;

    @Autowired
    private UserInterceptor userInterceptor;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(
        HttpServletRequest request, 
        HttpServletResponse response, 
        Object handler
    ) throws Exception {
    
        if(handler instanceof HandlerMethod handlerMethod) {
            if(handlerMethod.hasMethodAnnotation(AdminRole.class)) {
                boolean verify = this.authInterceptor.verify(request);
                if(!verify) {
                    throw new Exception();
                }
                return true;
            }
            if(handlerMethod.hasMethodAnnotation(UserRole.class)) {
                boolean verify = this.userInterceptor.verify(request);
                if(!verify) {
                    throw new Exception();
                }
                return true;
            }
        }
        return true;
    }

}
