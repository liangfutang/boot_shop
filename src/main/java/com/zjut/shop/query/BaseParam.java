package com.zjut.shop.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseParam implements Serializable {

    private Integer pagenum = 20;

    private Integer pagesize = 0;

}
