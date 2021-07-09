package com.zjut.shop.vo;

import lombok.Data;

@Data
public class PageResult<T> {

    public PageResult() {}

    public PageResult(Integer total, T t) {
        this.total = total;
        this.data = t;
    }

    // 总页数
    private Integer total;

    private T data;

}
