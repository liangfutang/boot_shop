package com.zjut.shop.vo;

import com.zjut.shop.query.BaseParam;
import lombok.Data;

import java.util.List;

@Data
public class CateVO extends BaseParam {

    /**
     * 分类 ID
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类父 ID
     */
    private Integer pid;

    /**
     * 分类当前层级
     */
    private Integer level;

    private Boolean isDeleted = false;

    /**
     * 子分类
     */
    private List<CateVO> children;
}
