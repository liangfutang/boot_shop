package com.zjut.shop.service;

import cn.hutool.core.bean.BeanUtil;
import com.zjut.shop.enums.AuthLevelEnum;
import com.zjut.shop.vo.AuthVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    /**
     * 操作类型
     */
    @Getter
    private static List<String> operateType = new ArrayList<>();

    /**
     * 存储所有的权限
     */
    private static final List<AuthVO> rightList = new CopyOnWriteArrayList<>();

    static {
        /**
         * 操作类型数据
         */
        operateType.add("list");
        operateType.add("tree");

        /**
         * 制作权限数据
         */
        AuthVO one = new AuthVO();
        one.setId(10);
        one.setAuthName("删除所有文件权限");
        one.setLevel(AuthLevelEnum.ONE.getCode());
        one.setPid(null);
        rightList.add(one);
        // 一级子权限
        AuthVO oneOne = new AuthVO();
        oneOne.setId(11);
        oneOne.setAuthName("删除项目部门文件权限");
        oneOne.setLevel(AuthLevelEnum.TWO.getCode());
        oneOne.setPid(one.getId());
        rightList.add(oneOne);
        AuthVO oneTwo = new AuthVO();
        oneTwo.setId(12);
        oneTwo.setAuthName("删除研发部门文件权限");
        oneTwo.setLevel(AuthLevelEnum.TWO.getCode());
        oneTwo.setPid(one.getId());
        rightList.add(oneTwo);
        AuthVO oneThree = new AuthVO();
        oneThree.setId(13);
        oneThree.setAuthName("删除产品部门文件权限");
        oneThree.setLevel(AuthLevelEnum.TWO.getCode());
        oneThree.setPid(one.getId());
        rightList.add(oneTwo);
        // 制作child
        List<AuthVO> oneChild = new ArrayList<>();
        oneChild.add(oneOne);
        oneChild.add(oneTwo);
        oneChild.add(oneThree);
        one.setChildren(oneChild);


        AuthVO two = new AuthVO();
        two.setId(20);
        two.setAuthName("修改所有文件权限");
        two.setLevel(AuthLevelEnum.ONE.getCode());
        two.setPid(null);
        rightList.add(two);
        // 一级子权限
        AuthVO twoOne = new AuthVO();
        twoOne.setId(21);
        twoOne.setAuthName("修改研发部门所有文件权限");
        twoOne.setLevel(AuthLevelEnum.TWO.getCode());
        twoOne.setPid(two.getId());
        rightList.add(twoOne);
        AuthVO twoTwo = new AuthVO();
        twoTwo.setId(22);
        twoTwo.setAuthName("修改产品部门所有文件权限");
        twoTwo.setLevel(AuthLevelEnum.TWO.getCode());
        twoTwo.setPid(two.getId());
        rightList.add(twoTwo);
        AuthVO twoThree = new AuthVO();
        twoThree.setId(23);
        twoThree.setAuthName("修改产品部门所有文件权限");
        twoThree.setLevel(AuthLevelEnum.TWO.getCode());
        twoThree.setPid(two.getId());
        rightList.add(twoThree);
        // 制作child
        List<AuthVO> twoChild = new ArrayList<>();
        twoChild.add(twoOne);
        twoChild.add(twoTwo);
        twoChild.add(twoThree);
        two.setChildren(twoChild);


        AuthVO three = new AuthVO();
        three.setId(30);
        three.setAuthName("新增所有文件权限");
        three.setLevel(AuthLevelEnum.ONE.getCode());
        three.setPid(null);
        rightList.add(three);
        // 一级子权限
        AuthVO threeOne = new AuthVO();
        threeOne.setId(31);
        threeOne.setAuthName("产品部门新增所有文件权限");
        threeOne.setLevel(AuthLevelEnum.TWO.getCode());
        threeOne.setPid(three.getId());
        rightList.add(threeOne);
        AuthVO threeTwo = new AuthVO();
        threeTwo.setId(32);
        threeTwo.setAuthName("研发部门新增所有文件权限");
        threeTwo.setLevel(AuthLevelEnum.TWO.getCode());
        threeTwo.setPid(three.getId());
        rightList.add(threeTwo);
        // 制作child
        List<AuthVO> threeChild = new ArrayList<>();
        threeChild.add(threeOne);
        threeChild.add(threeTwo);
        three.setChildren(threeChild);

        AuthVO four = new AuthVO();
        four.setId(40);
        four.setAuthName("查看所有文件权限");
        four.setLevel(AuthLevelEnum.ONE.getCode());
        four.setPid(null);
        rightList.add(four);
        // 制作child
        four.setChildren(new ArrayList<>());
    }

    @Override
    public List<AuthVO> selectRightList(String type) {
        if ("list".equals(type)) {
            return rightList.stream().map(one -> {
                AuthVO authVO = new AuthVO();
                BeanUtil.copyProperties(one, authVO);
                authVO.setChildren(null);
                return authVO;
            }).collect(Collectors.toList());
        }
        return rightList.stream().filter(one -> one.getPid() == null).collect(Collectors.toList());
    }
}
