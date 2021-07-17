package com.zjut.shop.enums;

import com.zjut.shop.query.BaseParam;
import lombok.Data;

@Data
public class FromUrlParam extends BaseParam {


    private Integer value;

    private String name;


    private String desc;

}
