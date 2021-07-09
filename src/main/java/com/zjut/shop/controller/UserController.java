package com.zjut.shop.controller;

import com.zjut.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/private/v1/users")
    public ResponseEntity<?> userListB() {
        return null;
    }
}
