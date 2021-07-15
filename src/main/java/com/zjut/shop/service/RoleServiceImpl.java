package com.zjut.shop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zjut.shop.enums.OperateEnum;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.query.RoleParam;
import com.zjut.shop.query.RoleRightParam;
import com.zjut.shop.vo.AuthVO;
import com.zjut.shop.vo.PageResult;
import com.zjut.shop.vo.RoleVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService, InitializingBean {

    @Autowired
    private AuthService authService;

    /**
     * 向角色中注入权限
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<AuthVO> authTree = authService.selectRightList(OperateEnum.AUTH_SELECT_TYPE_TREE.getCode());
        // 如果当前不存在权限，则跳出设置
        if (CollectionUtil.isEmpty(authTree)) return;

        for (RoleVO one : roleList) {
            List<AuthVO> authList = one.getAuthList();
            switch (one.getId()) {
                // 大老板，需要所有的权限
                case 1:
                    authList.addAll(authTree);
                    break;
                // 二老板，给他第一个和第二个
                case 2:
                    authList.addAll(authTree.size() >= 2 ? authTree.subList(0, 1) : authTree.subList(0, 2));
                    break;
                // 三老板，给他第一个和第三个
                case 3:
                    List<AuthVO> threeRole = new ArrayList<>();
                    threeRole.add(authTree.get(0));
                    if (threeRole.size() >= 3) threeRole.add(authTree.get(2));
                    authList.addAll(threeRole);
                    break;
                // 四老板，给他第一个
                case 4:
                    authList.addAll(authTree.subList(0, 1));
                    break;
                // 五老板，给他最后一个
                default:
                    authList.addAll(authTree.subList(authTree.size()-1, authTree.size()));
                    break;
            }
        }
    }

    /**
     * 存储所有的权限
     */
    @Getter
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
        three.setRoleDesc("总管部门内的所有事物");
        three.setRoleName("部门经理");
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

        RoleVO six = new RoleVO();
        six.setId(6);
        six.setRoleDesc("负责相关产品的设计");
        six.setRoleName("产品");
        roleList.add(six);
    }

    @Override
    public PageResult<List<RoleVO>> selectRoleList(RoleParam roleParam) {
        // 当前用户数
        int currentRoleTotal = roleList.size();
        // 查询的起点
        int start = roleParam.getPageNum() * roleParam.getPageSize();
        int end = start + roleParam.getPageSize();

        // 1. 如果起点超出范围则返回为空
        if (start > currentRoleTotal-1) {
            log.info("当前查询页超标啦");
            return new PageResult<>(currentRoleTotal, new ArrayList<>());
        }

        // 2. 如果终点超出了范围，则按最后一个算
        if (end > currentRoleTotal-1) {
            log.info("终点超出了");
            end = currentRoleTotal;
        }


        return new PageResult<>(currentRoleTotal, roleList.subList(start, end));
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
            log.info("当前没查到相关角色");
            throw new ShopRuntimeException(ResultStatus.NO_ROLE_FAILURE);
        }
        if (roles.size() > 1) {
            log.info("当前存在多个id相同的角色，数据异常");
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

    @Override
    public List<AuthVO> removeRoleRight(Integer roleId, Integer rightId) {
        List<AuthVO> authList = null;
        boolean findRole = false;
        for (RoleVO one : roleList) {
            if (!roleId.equals(one.getId())) continue;
            findRole = true;
            log.info("根据id找到对应的角色:{}", one);

            authList = one.getAuthList();
            if (CollectionUtil.isEmpty(authList)) {
                log.info("当前角色下面不存在对应的权限");
                throw new ShopRuntimeException(ResultStatus.NO_RIGHT_EXEC);
            }
            // 删除关系数据
            this.removeRight(authList, rightId);
        }
        if (!findRole) {
            log.info("没找到对应的权限");
            throw new ShopRuntimeException(ResultStatus.NO_ROLE_FAILURE);
        }

        return Optional.ofNullable(authList).orElseGet(ArrayList::new);
    }

    @Override
    public RoleVO addRoleRights(Integer roleId, RoleRightParam roleRightParam) {
        List<Integer> rids = roleRightParam.getRids();
        // 查看当前角色已经有的权限id列表,移除已经存在的权限id
        RoleVO roleVO = this.selectRoleById(roleId);
        List<AuthVO> authList = roleVO.getAuthList();
        this.removeExistRights(authList, rids);
        // 仅为展示功能，所以不区分权限等级了，全都添加到一级
        // 查出所有的权限列表
        List<AuthVO> collect = AuthServiceImpl.getRightList().stream().filter(one -> rids.contains(one.getId())).collect(Collectors.toList());
        authList.addAll(collect);
        return roleVO;
    }

    /**
     * 移除已经存在的权限id
     * @param authList
     * @param rightIds
     */
    private void removeExistRights(List<AuthVO> authList, List<Integer> rightIds) {
        if (CollectionUtil.isEmpty(authList)) return;

        authList.forEach(one -> {
            rightIds.remove(one.getId());
            this.removeExistRights(one.getChildren(), rightIds);
        });
    }

    /**
     * 删除关联权限数据
     * @param authList
     * @param rightId
     */
    private void removeRight(List<AuthVO> authList, Integer rightId) {
        for (AuthVO one: authList) {
            // 对本级过滤
            if (rightId.equals(one.getId())) {
                authList.remove(one);
                break;
            }
            // 过滤子权限
            if (CollectionUtil.isNotEmpty(one.getChildren())) {
                this.removeRight(one.getChildren(), rightId);
            }
        }
    }

}
