package com.zjut.shop.service;

import com.zjut.shop.vo.AuthVO;

import java.util.List;

public interface AuthService {

    /**
     * 根据条件查询所有权限列表
     * @param type
     * @return
     */
    List<AuthVO> selectRightList(String type);
}
