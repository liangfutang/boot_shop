package com.zjut.shop.vo;

import lombok.Data;

import java.util.List;

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
}
