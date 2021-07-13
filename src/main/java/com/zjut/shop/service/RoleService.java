package com.zjut.shop.service;

import com.zjut.shop.query.RoleParam;
import com.zjut.shop.vo.PageResult;
import com.zjut.shop.vo.RoleVO;

import java.util.List;

public interface RoleService {

    /**
     * 根据条件查询所有角色列表
     * @return
     */
    PageResult<List<RoleVO>> selectRoleList(RoleParam roleParam);

    /**
     * 新增一个用户
     * @param roleParam
     * @return
     */
    RoleVO addRoleList(RoleParam roleParam);

    /**
     * 根据id查找对应的角色
     * @param id
     * @return
     */
    RoleVO selectRoleById(Integer id);

    /**
     * 根据id编辑用户信息
     * @param id
     * @param role
     * @return
     */
    RoleVO updateRoleById(Integer id, RoleParam role);

    /**
     * 根据id删除角色及其子角色
     * @param id
     * @return
     */
    boolean deleteRoleById(Integer id);
}
