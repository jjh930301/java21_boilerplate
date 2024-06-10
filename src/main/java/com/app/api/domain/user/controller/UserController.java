package com.app.api.domain.user.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.api.domain.user.service.UserService;
import com.app.api.global.UserInfo;
import com.app.api.global.annotations.AdminRole;
import com.app.api.global.annotations.ApiController;
import com.app.api.global.annotations.SwaggerInfo;
import com.app.api.global.annotations.UserRole;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "user")
@ApiController
@RequestMapping(path = "user")
public class UserController {

  @Autowired
  private UserService userService;

  @SwaggerInfo(
        summary = "generate token"
    )
    @PostMapping("")
    public ResponseEntity<?> postToken() {
        System.out.println();
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.generateToken());
    }

    @SwaggerInfo(
        summary = "auth guard",
        securityRequirements = {"bearerAuth"}
    )
    @AdminRole
	@GetMapping(path = "auth")
	public ResponseEntity<?> getAuth(
        HttpServletRequest req
    ) {
        UserInfo admin = (UserInfo) req.getAttribute("admin");
		return ResponseEntity.status(HttpStatus.OK).body(admin);
	}

    @SwaggerInfo(
        summary = "user guard",
        securityRequirements = {"bearerAuth"}
    )
    @UserRole
    @GetMapping("user")
    public ResponseEntity<?> getUser() throws InterruptedException , ExecutionException  {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUsers());
    }

    @SwaggerInfo(
        summary = "none guard"
    )
    @GetMapping("none")
    public ResponseEntity<?> putMethodName() {  
        return ResponseEntity.status(HttpStatus.OK).body("test");
    }
}
