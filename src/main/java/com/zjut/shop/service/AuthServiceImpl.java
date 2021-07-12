package com.zjut.shop.service;

import com.zjut.shop.vo.AuthVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    /**
     * 存储所有的权限
     */
    private static final List<AuthVO> rightList = new CopyOnWriteArrayList<>();

    static {
        AuthVO one = new AuthVO();
        one.setId(10);
        one.setAuthName("删除所有文件权限");
        one.setLevel("1");
        one.setPid(null);
        rightList.add(one);
        // 一级子权限
        AuthVO oneOne = new AuthVO();
        oneOne.setId(11);
        oneOne.setAuthName("删除项目部门文件权限");
        oneOne.setLevel("11");
        oneOne.setPid(one.getId());
        rightList.add(oneOne);
        AuthVO oneTwo = new AuthVO();
        oneTwo.setId(12);
        oneTwo.setAuthName("删除研发部门文件权限");
        oneTwo.setLevel("12");
        oneTwo.setPid(one.getId());
        rightList.add(oneTwo);
        AuthVO oneThree = new AuthVO();
        oneThree.setId(13);
        oneThree.setAuthName("删除产品部门文件权限");
        oneThree.setLevel("13");
        oneThree.setPid(one.getId());
        rightList.add(oneTwo);


        AuthVO two = new AuthVO();
        two.setId(20);
        two.setAuthName("修改所有文件权限");
        two.setLevel("20");
        two.setPid(null);
        rightList.add(two);
        // 一级子权限
        AuthVO twoOne = new AuthVO();
        twoOne.setId(21);
        twoOne.setAuthName("修改研发部门所有文件权限");
        twoOne.setLevel("21");
        twoOne.setPid(two.getId());
        rightList.add(twoOne);
        AuthVO twoTwo = new AuthVO();
        twoTwo.setId(22);
        twoTwo.setAuthName("修改产品部门所有文件权限");
        twoTwo.setLevel("22");
        twoTwo.setPid(two.getId());
        rightList.add(twoTwo);
        AuthVO twoThree = new AuthVO();
        twoThree.setId(23);
        twoThree.setAuthName("修改产品部门所有文件权限");
        twoThree.setLevel("23");
        twoThree.setPid(two.getId());
        rightList.add(twoThree);


        AuthVO three = new AuthVO();
        three.setId(30);
        three.setAuthName("新增所有文件权限");
        three.setLevel("30");
        three.setPid(null);
        rightList.add(three);
        // 一级子权限
        AuthVO threeOne = new AuthVO();
        threeOne.setId(31);
        threeOne.setAuthName("产品部门新增所有文件权限");
        threeOne.setLevel("31");
        threeOne.setPid(three.getId());
        rightList.add(threeOne);
        AuthVO threeTwo = new AuthVO();
        threeTwo.setId(32);
        threeTwo.setAuthName("研发部门新增所有文件权限");
        threeTwo.setLevel("32");
        threeTwo.setPid(three.getId());
        rightList.add(threeTwo);

        AuthVO four = new AuthVO();
        four.setId(40);
        four.setAuthName("查看所有文件权限");
        four.setLevel("4");
        four.setPid(null);
        rightList.add(four);
    }

    @Override
    public List<AuthVO> selectRightList() {
        return null;
    }
}
