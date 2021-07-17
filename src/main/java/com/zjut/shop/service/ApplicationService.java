package com.zjut.shop.service;

import com.zjut.shop.query.ApplicationParam;
import com.zjut.shop.vo.ApplicationVO;
import com.zjut.shop.vo.PageResult;

import java.util.List;

public interface ApplicationService {


    /**
     * 根据条件查询应用
     * @return
     */
    PageResult<List<ApplicationVO>> selectApplicationList(ApplicationParam applicationParam);

    /**
     * 根据id修改应用的状态
     * @param id
     * @return
     */
    ApplicationVO changeStatusById(String id, String status);

    /**
     * 根据id查找对应的应用信息
     * @param id
     * @return
     */
    ApplicationVO selectAppById(String id);

    /**
     * 根据id删除应用信息
     * @param id
     * @return
     */
    boolean deleteAppById(String id);

    /**
     * 更新应用信息
     * @param applicationParam
     * @return
     */
    ApplicationVO updateApp(ApplicationParam applicationParam);
}
