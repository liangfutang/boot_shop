package com.zjut.shop.vo;

import lombok.Data;

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
    private String pid;
    private String path;
}
