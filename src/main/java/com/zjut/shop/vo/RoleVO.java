package com.zjut.shop.vo;

import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 角色
 */
@Data
public class RoleVO {

    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 子角色
     */
    private List<RoleVO> children;

    /**
     * 该角色下面所有的权限数据
     */
    private final List<AuthVO> authList = new CopyOnWriteArrayList<>();
}
