package com.zjut.shop.query;

import lombok.Data;

@Data
public class ApplicationParam extends BaseParam{

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

}
