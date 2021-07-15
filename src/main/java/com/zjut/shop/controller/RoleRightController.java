package com.zjut.shop.controller;

import com.zjut.shop.query.RoleRightParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 角色权限相关
 */
@RestController
@Slf4j
public class RoleRightController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色授权
     * @param roleId
     * @param roleRightParam
     * @return
     */
    @PostMapping("/api/private/v1/roles/{roleId}/rights")
    public ResponseEntity<?> addRoleRights(@PathVariable Integer roleId, @RequestBody RoleRightParam roleRightParam) {
        log.info("给角色id:{},分配权限:{}", roleId, roleRightParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("角色分配权限成功", roleService.addRoleRights(roleId, roleRightParam)), HttpStatus.OK);
    }

    /**
     * 删除角色指定权限
     * @param roleId
     * @param rightId
     * @return
     */
    @DeleteMapping("/api/private/v1/roles/{roleId}/rights/{rightId}")
    public ResponseEntity<?> removeRoleRight(@PathVariable Integer roleId, @PathVariable Integer rightId) {
        log.info("即将删除的权限,roleId:{},rightId:{}", roleId, rightId);
        return new ResponseEntity<>(BaseResult.handlerSuccess("删除对应角色成功", roleService.removeRoleRight(roleId, rightId)), HttpStatus.OK);
    }
}
