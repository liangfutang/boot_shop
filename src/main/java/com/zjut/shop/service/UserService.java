package com.zjut.shop.service;

import com.zjut.shop.query.UserAddParam;
import com.zjut.shop.query.UserParam;
import com.zjut.shop.vo.PageResult;
import com.zjut.shop.vo.UserVO;

import java.util.List;

public interface UserService {

    /**
     * 查询用户列表
     * @param userParam
     * @return
     */
    PageResult<List<UserVO>> selectList(UserParam userParam);

    /**
     * 根据id修改用户状态
     * @param id
     * @param mgState
     * @return
     */
    UserVO updateStatus(Integer id, Boolean mgState);

    /**
     * 新增用户
     * @param userAddParam
     * @return
     */
    UserVO addUser(UserAddParam userAddParam);

    UserVO deleteUserById(Integer id);
}
