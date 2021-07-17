package com.zjut.shop.vo;

import lombok.Data;

@Data
public class FromURLVO {

    /**
     * 短信前缀的代号
     */
    private Integer value;

    /**
     * 显示的短信前缀
     */
    private String name;

    /**
     * 短信前缀的描述
     */
    private String desc;

    /**
     * 创建时间字符串
     */
    private String gmtCreateStr;

    /**
     * 前缀的状态
     */
    private String status;
}
