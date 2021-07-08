package com.zjut.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 角色
 */
@Data
public class RoleVO {

    private Integer id;

    private String roleName;

    private String roleDesc;

    private List<RoleVO> children;
}
