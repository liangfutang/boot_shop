package com.zjut.shop.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseParam implements Serializable {

    // 当前页号，前端从1开始，后端从0开始计算
    private Integer pageNum = 0;

    // 每页的大小
    private Integer pageSize = 5;

}
