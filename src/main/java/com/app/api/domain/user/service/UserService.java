package com.app.api.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.app.api.domain.user.repository.UserJpaRepository;
import com.app.api.global.enums.AccessEnum;
import com.app.api.global.enums.TokenEnum;
import com.app.api.global.mappers.UserMapper;
import com.app.api.global.model.ApiRes;
import com.app.api.global.utils.JwtUtil;

@Service
public class UserService {

    
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final UserJpaRepository userRepo;
    
    public UserService(JwtUtil jwtUtil , UserMapper userMapper , UserJpaRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.userRepo = userRepo;
    }

    public ApiRes<HashMap<String , String>> generateToken() {
        String userToken = this.jwtUtil.createToken(
            1, 
            "user@user.com", 
            TokenEnum.ACCESS_TOKEN,
            AccessEnum.USER
        );
        String authToken = this.jwtUtil.createToken(
            2, 
            "admin@admin.com", 
            TokenEnum.ACCESS_TOKEN,
            AccessEnum.ADMIN
        );
        return new ApiRes<>(
            new HashMap<String, String>() {{
                put("userToken", userToken);
                put("authToken", authToken);
            }},
            "Get tokens"
        );
    }

    public ApiRes<HashMap<String , Object>> getUsers() throws InterruptedException , ExecutionException {

        var users = CompletableFuture.supplyAsync(() -> this.userMapper.findAll());
        
        var users2 =  CompletableFuture.supplyAsync(() -> this.userRepo.findAll());
        
        HashMap<String , Object> result = new HashMap<String , Object>(){{
            put("user", users.get());
            put("user2", users2.get());
        }};

        return new ApiRes<>(result , "Get users");
    }
}
