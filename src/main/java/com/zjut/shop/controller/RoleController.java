package com.zjut.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @GetMapping("/api/private/v1/roles")
    public ResponseEntity<?> selectRoleList() {

        return null;
    }

}
