package com.zjut.shop.service;

import com.zjut.shop.query.CateParam;
import com.zjut.shop.vo.CateVO;
import com.zjut.shop.vo.PageResult;

import java.util.List;

public interface CateService {

    /**
     * 查找列表
     * @return
     */
    PageResult<List<CateVO>> selectList(CateParam cateParam);
}
