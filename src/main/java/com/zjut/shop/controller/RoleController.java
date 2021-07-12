package com.zjut.shop.controller;

import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/api/private/v1/roles")
    public ResponseEntity<?> selectRoleList() {
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询权限列表成功", roleService.selectRoleList()), HttpStatus.OK);
    }

}
