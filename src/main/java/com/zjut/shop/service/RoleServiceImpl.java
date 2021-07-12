package com.zjut.shop.service;

import com.zjut.shop.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    /**
     * 存储所有的权限
     */
    private static final List<RoleVO> rightList = new CopyOnWriteArrayList<>();

    static {

    }

    @Override
    public List<RoleVO> selectRoleList(String type) {

        return rightList;
    }
}
