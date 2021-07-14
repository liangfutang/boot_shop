package com.zjut.shop.query;

import lombok.Data;

import java.util.List;

@Data
public class RoleRightParam {

    /**
     * 权限的id列表
     */
    private List<Integer> rids;
}
