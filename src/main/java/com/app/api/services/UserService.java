package com.app.api.services;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.api.enums.AccessEnum;
import com.app.api.enums.TokenEnum;
import com.app.api.global.ApiRes;
import com.app.api.mappers.UserMapper;
import com.app.api.utils.JwtUtil;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserMapper userMapper;

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
    Executor vt = Executors.newVirtualThreadPerTaskExecutor();

    CompletableFuture<List<HashMap<String, String>>> users = 
        CompletableFuture.supplyAsync(() -> this.userMapper.findAll() , vt);
    
    CompletableFuture<List<HashMap<String, String>>> users2 = 
        CompletableFuture.supplyAsync(() -> this.userMapper.findAll() , vt);
    
    System.out.println(Thread.currentThread().isVirtual());
    HashMap<String , Object> result = new HashMap<String , Object>(){{
        put("user", users.get());
        put("user2", users2.get());
    }};

    return new ApiRes<>(result , "Get users");
}
}
