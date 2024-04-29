package com.app.api.global.exceptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String , Object>> globalException(Exception e) {
		Map<String, Object> map = new HashMap<>();
		map.put("message" , Arrays.asList(e.getMessage()));
		map.put("payload", null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

}