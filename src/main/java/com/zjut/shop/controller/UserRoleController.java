package com.zjut.shop.controller;

import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserRoleController {

    @Autowired
    private UserService userService;

    /**
     * 为用户分配一个角色
     * @param userId
     * @param roleId
     * @return
     */
    @PostMapping("/api/private/v1/user/{userId}/role/{roleId}")
    public ResponseEntity<?> addUserRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
        log.info("为用户id:{},授权角色id:{}", userId, roleId);
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询角色列表成功", userService.addUserRole(userId, roleId)), HttpStatus.OK);
    }

}
