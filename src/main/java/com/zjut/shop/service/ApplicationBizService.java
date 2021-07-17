package com.zjut.shop.service;

import com.zjut.shop.query.ApplicationBizParam;
import com.zjut.shop.vo.ApplicationBizVO;
import com.zjut.shop.vo.PageResult;

import java.util.List;

public interface ApplicationBizService {

    /**
     * 按照条件查询应用业务
     * @param applicationBizParam
     * @return
     */
    PageResult<List<ApplicationBizVO>> selectApplicationBizList(ApplicationBizParam applicationBizParam);

    /**
     * 根据业务code查找
     * @return
     */
    ApplicationBizVO selectByBizCode(String bizCode);

    /**
     * 新增应用业务
     * @param applicationBizParam
     * @return
     */
    ApplicationBizVO addApplicationBiz(ApplicationBizParam applicationBizParam);

    /**
     * 根据bizCode修改应用业务
     * @param applicationBizParam
     * @return
     */
    ApplicationBizVO updateByBizCode(ApplicationBizParam applicationBizParam);

    /**
     * 根据bizCode修改应用业务状态
     * @param bizCode
     * @param status
     * @return
     */
    ApplicationBizVO changeStatusByBizCode(String bizCode, String status);


    /**
     * 根据bizCode删除应用业务状态
     * @param bizCode
     * @return
     */
    boolean deleteByBizCode(String bizCode);
}
