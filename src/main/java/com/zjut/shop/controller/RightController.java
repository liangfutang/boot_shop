package com.zjut.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RightController {

    @GetMapping("/api/private/v1/right/list")
    public ResponseEntity<?> selectRightList() {

        return null;
    }
}
