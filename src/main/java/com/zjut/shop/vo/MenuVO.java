package com.zjut.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * 菜单
 */
@Data
public class MenuVO {

    private Integer id;

    private String menuName;

    /**
     * 关联的权限id
     */
    private Integer authId;

    /**
     * 父菜单id
     */
    private Integer pid;

    private String path;

    /**
     * 子权限
     */
    private List<MenuVO> children;
}
