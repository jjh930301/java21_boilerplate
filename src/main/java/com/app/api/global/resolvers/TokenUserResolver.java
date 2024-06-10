package com.app.api.global.resolvers;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.app.api.global.annotations.TokenUser;
import com.app.api.global.model.UserInfo;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class TokenUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(
      @NonNull MethodParameter parameter, 
      @Nullable ModelAndViewContainer mavContainer, 
      @NonNull NativeWebRequest webRequest, 
      @Nullable WebDataBinderFactory binderFactory
    ) throws Exception {
      HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
      UserInfo currentUser = (UserInfo) request.getAttribute("admin") != null ? 
        (UserInfo) request.getAttribute("admin") : 
        (UserInfo) request.getAttribute("user");
      return currentUser;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TokenUser.class);
    }    
}