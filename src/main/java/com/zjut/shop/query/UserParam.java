package com.zjut.shop.query;

import lombok.Data;

@Data
public class UserParam extends BaseParam{

    public UserParam() {}
    public UserParam(Integer pageNum, Integer pageSize, String query) {
        this.setPageSize(pageSize);
        this.setPageNum(pageNum);
        this.query = query;
    }

    private String query;
}
