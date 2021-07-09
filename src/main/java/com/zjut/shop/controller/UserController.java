package com.zjut.shop.controller;

import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class UserController {

    /**
     * 计算访问次数，用来为前端大体模拟访问成功和失败场景
     */
    private final AtomicInteger count = new AtomicInteger();

    @Autowired
    private UserService userService;

    @GetMapping("/api/private/v1/users")
    public ResponseEntity<?> userListB() {
        int currentCount = count.getAndIncrement();
        if (currentCount > Integer.MAX_VALUE-10) {
            count.set(0);
        }

        // 模拟的场景数
        int scenes = 3;

        // 模拟异常场景，http状态非200
        if (currentCount % scenes == 2) {
            log.info("模拟异常场景，状态码非200的");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.SELECT_EXEC.getMsg() + "400 status", ResultStatus.SELECT_EXEC.getCode()), HttpStatus.BAD_REQUEST);
        }

        // 模拟异常场景，http状态非200
        if (currentCount % scenes == 1) {
            log.info("模拟异常场景，状态码是200的");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.SELECT_EXEC.getMsg() + "200 status", ResultStatus.SELECT_EXEC.getCode()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new BaseResult("查询成功", 200, userService.selectList()), HttpStatus.OK);
    }
}
