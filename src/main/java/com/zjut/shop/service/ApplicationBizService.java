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
}
