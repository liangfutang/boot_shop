package com.zjut.shop.controller;

import com.zjut.shop.response.BaseResult;
import com.zjut.shop.vo.AuthVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    /**
     * 导航栏左侧菜单列表
     * @return
     */
    @GetMapping("/api/private/v1/menus")
    public ResponseEntity<?> menusList() {
        List<AuthVO> menus = new ArrayList<>();
        // 用户列表
        AuthVO userManger = new AuthVO();
        userManger.setId(110);
        userManger.setAuthName("用户列表");
        userManger.setPath("/api/private/v1/users");

        // 权限管理
        AuthVO rights = new AuthVO();
        rights.setId(103);
        rights.setAuthName("权限管理");
        rights.setPath("/api/private/v1/rights");

        // 商品管理
        AuthVO goods = new AuthVO();
        goods.setId(101);
        goods.setAuthName("商品管理");
        goods.setPath("/api/private/v1/goods");

        // 订单管理
        AuthVO orders = new AuthVO();
        orders.setId(102);
        orders.setAuthName("订单管理");
        orders.setPath("/api/private/v1/orders");

        // 数据统计
        AuthVO reports = new AuthVO();
        reports.setId(145);
        reports.setAuthName("数据统计");
        reports.setPath("/api/private/v1/reports");


        menus.add(userManger);
        menus.add(rights);
        menus.add(goods);
        menus.add(orders);
        menus.add(reports);

        return new ResponseEntity<>(new BaseResult("获取菜单成功", 200, menus), HttpStatus.OK);
    }
}
