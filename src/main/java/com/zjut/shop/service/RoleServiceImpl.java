package com.zjut.shop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.query.RoleParam;
import com.zjut.shop.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    /**
     * 存储所有的权限
     */
    private static final List<RoleVO> roleList = new CopyOnWriteArrayList<>();

    static {
        RoleVO one = new RoleVO();
        one.setId(1);
        one.setRoleDesc("管理所有部门");
        one.setRoleName("总裁");
        roleList.add(one);

        RoleVO two = new RoleVO();
        two.setId(2);
        two.setRoleDesc("总管公司所有事物执行");
        two.setRoleName("总经理");
        roleList.add(two);

        RoleVO three = new RoleVO();
        three.setId(3);
        three.setRoleDesc("部门经理");
        three.setRoleName("总管部门内的所有事物");
        roleList.add(three);

        RoleVO four = new RoleVO();
        four.setId(4);
        four.setRoleDesc("总管小组内部的事务");
        four.setRoleName("高级经理");
        roleList.add(four);

        RoleVO five = new RoleVO();
        five.setId(5);
        five.setRoleDesc("负责相关产品的研发");
        five.setRoleName("研发");
        roleList.add(five);
    }

    @Override
    public List<RoleVO> selectRoleList() {
        return roleList;
    }

    @Override
    public RoleVO addRoleList(RoleParam roleParam) {
        RoleVO addOne = new RoleVO();
        BeanUtil.copyProperties(roleParam, addOne);
        if (roleList.size() == 0) {
            addOne.setId(0);
        } else {
            RoleVO maxIdRole = roleList.stream().max(Comparator.comparing(RoleVO::getId)).get();
            addOne.setId(maxIdRole.getId() + 1);
        }
        roleList.add(addOne);
        return addOne;
    }

    @Override
    public RoleVO selectRoleById(Integer id) {
        List<RoleVO> roles = roleList.stream().filter(one -> id.equals(one.getId())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(roles)) {
            log.info("当前没查到相关用户");
            throw new ShopRuntimeException(ResultStatus.NO_ROLE_FAILURE);
        }
        if (roles.size() > 1) {
            log.info("当前存在多个id相同的用户，数据异常");
            throw new ShopRuntimeException(ResultStatus.NO_ROLE_FAILURE);
        }
        log.info("过滤出来的角色:{}", roles);
        return roles.get(0);
    }

    @Override
    public RoleVO updateRoleById(Integer id, RoleParam role) {
        RoleVO roleVO = this.selectRoleById(id);
        String roleName = role.getRoleName();
        if (StringUtils.isNotBlank(roleName)) {
            roleVO.setRoleName(roleName);
        }

        String roleDesc = role.getRoleDesc();
        if (StringUtils.isNotBlank(roleDesc)) {
            roleVO.setRoleDesc(roleDesc);
        }
        return roleVO;
    }

    @Override
    public boolean deleteRoleById(Integer id) {
        return roleList.removeIf(one -> id.equals(one.getId()));
    }
}
