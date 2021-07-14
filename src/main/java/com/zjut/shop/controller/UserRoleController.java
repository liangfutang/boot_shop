package com.zjut.shop.controller;

import com.zjut.shop.query.UserRoleParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserRoleController {

    @PostMapping("/api/private/v1/user/{id}/roles")
    public ResponseEntity<?> addUserRoles(@PathVariable Integer id, @RequestBody UserRoleParam userRoleParam) {
        log.info("为用户id:{},授权:{}", id, userRoleParam);

        return null;
    }

}
