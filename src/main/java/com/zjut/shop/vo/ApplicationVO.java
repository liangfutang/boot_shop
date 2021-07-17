package com.zjut.shop.vo;

import lombok.Data;

@Data
public class ApplicationVO {
    /**
     * 主键(应用id)
     */
    private String id;
    /**
     * 调用者的服务名
     */
    private String applicationName;
    /**
     * 联系人，负责人
     */
    private String principal;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private java.util.Date gmtCreate;
    /**
     * 创建时间字符串
     */
    private String gmtCreateStr;
}
