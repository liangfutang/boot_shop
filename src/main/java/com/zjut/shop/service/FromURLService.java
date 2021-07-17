package com.zjut.shop.service;

import com.zjut.shop.enums.FromUrlParam;
import com.zjut.shop.vo.FromURLVO;
import com.zjut.shop.vo.PageResult;

import java.util.List;

public interface FromURLService {

    /**
     * 查找前缀列表
     * @param fromUrlParam
     * @return
     */
    PageResult<List<FromURLVO>> selectList(FromUrlParam fromUrlParam);

    /**
     * 根据前缀值查找前缀
     * @param value
     * @return
     */
    FromURLVO selectListByValue(Integer value);

    /**
     * 根据前缀值修改状态
     * @param value
     * @param status
     * @return
     */
    FromURLVO changeStatusById(Integer value, String status);

    /**
     * 根据value删除对应的前缀
     * @param value
     * @return
     */
    boolean deleteFromUrlByValue(Integer value);

    /**
     * 根据前缀值修改名称及描述
     * @param fromUrlParam
     * @return
     */
    FromURLVO updateFromUrl(FromUrlParam fromUrlParam);

    /**
     * 新增一个短信前缀
     * @param fromUrlParam
     * @return
     */
    FromURLVO addFromUrl(FromUrlParam fromUrlParam);
}
