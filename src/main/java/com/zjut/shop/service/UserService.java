package com.zjut.shop.service;

import com.zjut.shop.query.UserParam;
import com.zjut.shop.vo.PageResult;
import com.zjut.shop.vo.UserVO;

import java.util.List;

public interface UserService {

    PageResult<List<UserVO>> selectList(UserParam userParam);
}
