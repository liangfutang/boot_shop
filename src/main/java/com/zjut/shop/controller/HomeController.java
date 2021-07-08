package com.zjut.shop.controller;

import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.vo.AuthVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class HomeController {

    /**
     * 计算访问次数，用来为前端大体模拟访问成功和失败场景
     */
    private AtomicInteger count = new AtomicInteger();

    /**
     * 导航栏左侧菜单列表
     * @return
     */
    @GetMapping("/api/private/v1/menus")
    public ResponseEntity<?> menusList(@RequestParam(defaultValue = "0") Integer type) {
        log.info("本次收到的请求类型:{}", type);

        List<AuthVO> menus = new ArrayList<>();

        if (count.get() >= Integer.MAX_VALUE-10) {
            count.set(0);
        }
        int i = count.incrementAndGet();
        // 模拟请求失败的场景，每三次一个失败
        if (i%4 == 3) {
            log.info("模拟异常场景");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.MENU_EXEC.getMsg(), ResultStatus.MENU_EXEC.getCode()), HttpStatus.OK);
        }
        // 模拟数据为空的场景
        if (i%4 == 2) {
            log.info("模拟获取到空菜单的场景");
            return new ResponseEntity<>(BaseResult.handlerSuccess("获取菜单成功", menus), HttpStatus.OK);
        }


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
