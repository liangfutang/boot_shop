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
        log.info("本次收到的请求类型:{},count:{}", type, count.get());

        List<AuthVO> menus = new ArrayList<>();

        if (count.get() >= Integer.MAX_VALUE-10) {
            count.set(0);
        }
        int i = count.incrementAndGet();
        // 模拟请求失败的场景，每三次一个失败
        if (i%4 == 3) {
            log.info("模拟异常场景，状态码是200的");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.MENU_EXEC.getMsg() + "200 status", ResultStatus.MENU_EXEC.getCode()), HttpStatus.OK);
        }
        if (i%4 == 1) {
            log.info("模拟异常场景，状态码是400的");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.MENU_EXEC.getMsg() + "400 status", ResultStatus.MENU_EXEC.getCode()), HttpStatus.BAD_REQUEST);
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
        // 用户列表的二级导航
        List<AuthVO> userChildren = new ArrayList<>();
        AuthVO oneUser = new AuthVO();
        oneUser.setId(111);
        oneUser.setAuthName("B端用户");
        oneUser.setPath("/api/private/v1/users/b");
        userChildren.add(oneUser);
        AuthVO towUser = new AuthVO();
        towUser.setId(112);
        towUser.setAuthName("C端用户");
        towUser.setPath("/api/private/v1/users/c");
        AuthVO threeUser = new AuthVO();
        userChildren.add(towUser);
        threeUser.setId(113);
        threeUser.setAuthName("VIP用户");
        threeUser.setPath("/api/private/v1/users/v");
        userChildren.add(threeUser);
        userManger.setChildren(userChildren);


        // 权限管理
        AuthVO rights = new AuthVO();
        rights.setId(130);
        rights.setAuthName("权限管理");
        rights.setPath("/api/private/v1/rights");

        // 商品管理
        AuthVO goods = new AuthVO();
        goods.setId(150);
        goods.setAuthName("商品管理");
        // 商品列表的二级导航
        List<AuthVO> goodsChildren = new ArrayList<>();
        AuthVO apple = new AuthVO();
        apple.setId(151);
        apple.setAuthName("苹果");
        apple.setPath("/api/private/v1/goods/ap");
        goodsChildren.add(apple);
        AuthVO oringe = new AuthVO();
        oringe.setId(152);
        oringe.setAuthName("橘子");
        oringe.setPath("/api/private/v1/goods/or");
        goodsChildren.add(oringe);
//        // 三级菜单导航
//        List<AuthVO> suOrgChildren = new ArrayList<>();
//        AuthVO anhuiOringe = new AuthVO();
//        anhuiOringe.setId(1521);
//        anhuiOringe.setAuthName("安徽橘子");
//        anhuiOringe.setPath("/api/private/v1/goods/or/1");
//        suOrgChildren.add(anhuiOringe);
//        AuthVO jiangsuOringe = new AuthVO();
//        jiangsuOringe.setId(1522);
//        jiangsuOringe.setAuthName("江苏橘子");
//        jiangsuOringe.setPath("/api/private/v1/goods/or/2");
//        suOrgChildren.add(jiangsuOringe);
//        oringe.setChildren(suOrgChildren);

        AuthVO balana = new AuthVO();
        balana.setId(153);
        balana.setAuthName("香蕉");
        balana.setPath("/api/private/v1/goods/bl");
        goodsChildren.add(balana);
        goods.setChildren(goodsChildren);

        // 订单管理
        AuthVO orders = new AuthVO();
        orders.setId(170);
        orders.setAuthName("订单管理");

        // 数据统计
        AuthVO reports = new AuthVO();
        reports.setId(190);
        reports.setAuthName("数据统计");

        menus.add(userManger);
        menus.add(rights);
        menus.add(goods);
        menus.add(orders);
        menus.add(reports);

        return new ResponseEntity<>(new BaseResult("获取菜单成功", 200, menus), HttpStatus.OK);
    }
}
