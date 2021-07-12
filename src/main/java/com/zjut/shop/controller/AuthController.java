package com.zjut.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/api/private/v1/right/{type}")
    public ResponseEntity<?> selectRightList(@PathVariable String type) {

        return null;
    }
}
