package com.zjut.shop.controller;

import com.zjut.shop.response.BaseResult;
import com.zjut.shop.vo.MenuVO;
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
        log.info("本次收到的请求类型:{},count:{}", type, count.get());

        List<MenuVO> menus = new ArrayList<>();

        if (count.get() >= Integer.MAX_VALUE-10) {
            count.set(0);
        }
//        int i = count.incrementAndGet();
//        // 模拟请求失败的场景，每三次一个失败
//        if (i%4 == 3) {
//            log.info("模拟异常场景，状态码是200的");
//            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.MENU_EXEC.getMsg() + "200 status", ResultStatus.MENU_EXEC.getCode()), HttpStatus.OK);
//        }
//        if (i%4 == 1) {
//            log.info("模拟异常场景，状态码是400的");
//            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.MENU_EXEC.getMsg() + "400 status", ResultStatus.MENU_EXEC.getCode()), HttpStatus.BAD_REQUEST);
//        }
//        // 模拟数据为空的场景
//        if (i%4 == 2) {
//            log.info("模拟获取到空菜单的场景");
//            return new ResponseEntity<>(BaseResult.handlerSuccess("获取菜单成功", menus), HttpStatus.OK);
//        }


        // 用户列表
        MenuVO userManger = new MenuVO();
        userManger.setId(110);
        userManger.setMenuName("用户管理");
        // 用户列表的二级导航
        List<MenuVO> userChildren = new ArrayList<>();
        MenuVO oneUser = new MenuVO();
        oneUser.setId(111);
        oneUser.setMenuName("用户列表");
        oneUser.setPath("/users");
        userChildren.add(oneUser);
        userManger.setChildren(userChildren);


        // 权限管理
        MenuVO rights = new MenuVO();
        rights.setId(130);
        rights.setMenuName("权限管理");
        // 权限管理的二级导航
        List<MenuVO> rightsChildren = new ArrayList<>();
        MenuVO one = new MenuVO();
        one.setId(130);
        one.setMenuName("权限列表");
        one.setPath("/rights");
        rightsChildren.add(one);
        rights.setChildren(rightsChildren);

        // 商品管理
        MenuVO goods = new MenuVO();
        goods.setId(150);
        goods.setMenuName("商品管理");
        // 商品列表的二级导航
        List<MenuVO> goodsChildren = new ArrayList<>();
        MenuVO apple = new MenuVO();
        apple.setId(151);
        apple.setMenuName("苹果");
        apple.setPath("/goods");
        goodsChildren.add(apple);
        MenuVO oringe = new MenuVO();
        oringe.setId(152);
        oringe.setMenuName("橘子");
        oringe.setPath("/api/private/v1/goods/or");
        goodsChildren.add(oringe);

        MenuVO balana = new MenuVO();
        balana.setId(153);
        balana.setMenuName("香蕉");
        balana.setPath("/api/private/v1/goods/bl");
        goodsChildren.add(balana);
        goods.setChildren(goodsChildren);

        // 订单管理
        MenuVO orders = new MenuVO();
        orders.setId(170);
        orders.setMenuName("订单管理");

        // 数据统计
        MenuVO reports = new MenuVO();
        reports.setId(190);
        reports.setMenuName("数据统计");

        menus.add(userManger);
        menus.add(rights);
        menus.add(goods);
        menus.add(orders);
        menus.add(reports);

        return new ResponseEntity<>(new BaseResult("获取菜单成功", 200, menus), HttpStatus.OK);
    }
}
