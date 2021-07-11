package com.zjut.shop.controller;

import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.query.UserAddParam;
import com.zjut.shop.query.UserParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.UserService;
import com.zjut.shop.vo.PageResult;
import com.zjut.shop.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> userList(@RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(required = false) String query) {
        log.info(String.format("获取用户列表,当前页:%d,每页:%d,查询条件:%s", pageNum, pageSize, query));
        if (pageNum < 1) {
            log.error("起始页不能小于1");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.PAGE_NUM_EXEC.getMsg(), ResultStatus.PAGE_NUM_EXEC.getCode()), HttpStatus.OK);
        }
        if (pageSize<1 || pageSize>50) {
            log.error("每页大小不规范");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.PAGE_SIZE_EXEC.getMsg(), ResultStatus.PAGE_SIZE_EXEC.getCode()), HttpStatus.OK);
        }

        int currentCount = count.getAndIncrement();
        if (currentCount > Integer.MAX_VALUE-10) {
            count.set(0);
        }

        // 模拟的场景数
//        int scenes = 4;

        // 模拟异常场景，http状态非200
//        if (currentCount % scenes == 3) {
//            log.info("模拟异常场景，状态码非200的");
//            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.SELECT_EXEC.getMsg() + "400 status", ResultStatus.SELECT_EXEC.getCode()), HttpStatus.BAD_REQUEST);
//        }

        // 模拟异常场景，http状态非200
//        if (currentCount % scenes == 2) {
//            log.info("模拟异常场景，状态码是200的");
//            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.SELECT_EXEC.getMsg() + "200 status", ResultStatus.SELECT_EXEC.getCode()), HttpStatus.OK);
//        }

        // 模拟正常场景，前端从1开始计算页数，后端从0开始计算
        PageResult<List<UserVO>> userVOS = userService.selectList(new UserParam(pageNum-1, pageSize, query));

        return new ResponseEntity<>(new BaseResult("查询成功", 200, userVOS), HttpStatus.OK);
    }

    @PutMapping("/api/private/v1/users/{id}/state/{mgState}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @PathVariable Boolean mgState) {
        log.info("修改用户状态数据,用户id:{},状态:{}", id, mgState);
        try {
            return new ResponseEntity<>(BaseResult.handlerSuccess("更新用户状态成功", userService.updateStatus(id, mgState)), HttpStatus.OK);
        } catch (ShopRuntimeException sre){
            log.error("更新用户状态内部异常");
            return new ResponseEntity<>(BaseResult.handlerFailure(sre.getMessage(), sre.getCode()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("更新用户状态异常");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.UPDATE_USER_STATUS_FAILURE.getMsg(), ResultStatus.UPDATE_USER_STATUS_FAILURE.getCode()), HttpStatus.OK);
        }
    }


    @PostMapping("/api/private/v1/users")
    public ResponseEntity<?> addUser(@RequestBody UserAddParam userAddParam) {
        log.info("新添加一个用户:{}", userAddParam);
        try {
            return new ResponseEntity<>(BaseResult.handlerSuccess("更新用户状态成功", userService.addUser(userAddParam)), HttpStatus.OK);
        } catch (Exception e) {
            log.error("添加用户异常:", e);
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.ADD_USER_EXEC.getMsg(), ResultStatus.ADD_USER_EXEC.getCode()), HttpStatus.OK);
        }
    }
}
