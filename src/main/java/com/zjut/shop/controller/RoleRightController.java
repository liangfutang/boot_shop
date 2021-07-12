package com.zjut.shop.controller;

import com.zjut.shop.query.RoleRightParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 角色权限相关
 */
@RestController
public class RoleRightController {

    /**
     * 角色授权
     * @param roleId
     * @param roleRightParam
     * @return
     */
    @PostMapping("/api/private/v1/roles/{roleId}/rights")
    public ResponseEntity<?> addRoleRights(@PathVariable Integer roleId, @RequestBody RoleRightParam roleRightParam) {

        return null;
    }

    /**
     * 删除角色指定权限
     * @param roleId
     * @param rightId
     * @return
     */
    @DeleteMapping("/api/private/v1/roles/{roleId}/rights/{rightId}")
    public ResponseEntity<?> deleteRoleRight(@PathVariable Integer roleId, @PathVariable Integer rightId) {

        return null;
    }
}
