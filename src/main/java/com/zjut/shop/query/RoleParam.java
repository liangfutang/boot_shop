package com.zjut.shop.query;

import lombok.Data;

@Data
public class RoleParam extends BaseParam{

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    public RoleParam() {}

    public RoleParam(Integer pageNum, Integer pageSize) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
    }

    public RoleParam(Integer pageNum, Integer pageSize, String roleName, String roleDesc) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
    }
}
