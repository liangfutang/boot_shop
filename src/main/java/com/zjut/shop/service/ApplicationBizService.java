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
     * 根据业务id查找
     * @return
     */
    ApplicationBizVO selectByBizCode(Integer id);

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
     * @param id
     * @param status
     * @return
     */
    ApplicationBizVO changeStatusByBizCode(Integer id, String status);


    /**
     * 根据bizCode删除应用业务状态
     * @param bizCode
     * @return
     */
    boolean deleteByBizCode(Integer Id);
}
