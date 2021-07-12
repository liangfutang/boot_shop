package com.zjut.shop.service;

import com.zjut.shop.vo.AuthVO;

import java.util.List;

public interface AuthService {

    List<AuthVO> selectRightList();
}
