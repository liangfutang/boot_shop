package com.zjut.shop.vo;

import lombok.Data;

@Data
public class ApplicationBizVO {

    /**
     * 应用id
     */
    private String applicationId;

    /**
     * 业务code
     */
    private String bizCode;

    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 短信前缀
     */
    private Integer fromUrl;

    /**
     * 业务状态
     */
    private String status;
    /**
     * 创建时间字符串
     */
    private String gmtCreateStr;
}