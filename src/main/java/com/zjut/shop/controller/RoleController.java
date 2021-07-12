package com.zjut.shop.controller;

import com.zjut.shop.query.RoleParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.RoleService;
import com.zjut.shop.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     * @return
     */
    @GetMapping("/api/private/v1/roles")
    public ResponseEntity<?> selectRoleList() {
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询角色列表成功", roleService.selectRoleList()), HttpStatus.OK);
    }

    /**
     *  添加角色
     * @param roleParam
     * @return
     */
    @PostMapping("/api/private/v1/roles")
    public ResponseEntity<?> addRole(@RequestBody RoleParam roleParam) {
        log.info("新增角色参数:{}", roleParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("新增角色列表成功", roleService.addRoleList(roleParam)), HttpStatus.OK);
    }

    /**
     *  根据 ID 查询角色
     * @param id
     * @return
     */
    @GetMapping("/api/private/v1/roles/{id}")
    public ResponseEntity<?> selectRoleById(@PathVariable Integer id) {
        log.info("根据if查找角色,id:{}",id);

        return null;
    }

    /**
     *  编辑提交角色
     * @param id
     * @param role
     * @return
     */
    @PutMapping("/api/private/v1/roles/{id}")
    public ResponseEntity<?> updateRoleById(@PathVariable Integer id, @RequestBody RoleVO role) {
        log.info("根据id:{},修改角色参数:{}", id, role);

        return null;
    }


    /**
     *  删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/api/private/v1/roles/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable Integer id) {
        log.info("根据id删除角色:{}", id);

        return null;
    }

}
