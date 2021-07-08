package com.zjut.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 权限
 */
@Data
public class AuthVO {

    private Integer id;

    private String authName;

    private String level;

    /**
     * 父权限id
     */
    private Integer pid;

    private String path;

    /**
     * 子权限
     */
    private List<AuthVO> children;
}
