package com.zjut.shop.service;

import com.zjut.shop.vo.AuthVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final List<AuthVO> rightList = new CopyOnWriteArrayList<>();

    static {
        AuthVO one = new AuthVO();
        one.setId(0);
        one.setAuthName("删除所有文件权限");
        one.setLevel("0");
        one.setPid(null);
        rightList.add(one);

        AuthVO two = new AuthVO();
        two.setId(1);
        two.setAuthName("修改所有文件权限");
        two.setLevel("1");
        two.setPid(null);
        rightList.add(two);

        AuthVO three = new AuthVO();
        three.setId(2);
        three.setAuthName("新增所有文件权限");
        three.setLevel("2");
        three.setPid(null);
        rightList.add(three);

        AuthVO four = new AuthVO();
        four.setId(3);
        four.setAuthName("查看所有文件权限");
        four.setLevel("3");
        four.setPid(null);
        rightList.add(four);
    }

    @Override
    public List<AuthVO> selectRightList() {
        return null;
    }
}
