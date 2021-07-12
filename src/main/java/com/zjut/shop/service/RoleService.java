package com.zjut.shop.service;

import com.zjut.shop.vo.RoleVO;

import java.util.List;

public interface RoleService {

    /**
     * 根据条件查询所有角色列表
     * @param type
     * @return
     */
    List<RoleVO> selectRoleList(String type);
}
