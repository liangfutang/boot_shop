package com.zjut.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 权限
 */
@Data
public class AuthVO {

    private Integer id;
    /**
     * 权限名称
     */
    private String authName;
    /**
     * 权限等级
     */
    private String level;
    /**
     * 父权限id
     */
    private Integer pid;

    /**
     * 子权限
     */
    private List<AuthVO> children;
}
