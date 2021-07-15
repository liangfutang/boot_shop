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
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 查询用户列表
     * @param pageNum
     * @param pageSize
     * @param query
     * @return
     */
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

        PageResult<List<UserVO>> userVOS = userService.selectList(new UserParam(pageNum-1, pageSize, query));

        return new ResponseEntity<>(new BaseResult("查询成功", 200, userVOS), HttpStatus.OK);
    }

    /**
     * 修改用户状态
     * @param id
     * @param mgState
     * @return
     */
    @PutMapping("/api/private/v1/users/{id}/state/{mgState}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @PathVariable Boolean mgState) {
        log.info("修改用户状态数据,用户id:{},状态:{}", id, mgState);
        return new ResponseEntity<>(BaseResult.handlerSuccess("更新用户状态成功", userService.updateStatus(id, mgState)), HttpStatus.OK);
    }

    /**
     * 添加一个用户
     * @param userAddParam
     * @return
     */
    @PostMapping("/api/private/v1/users")
    public ResponseEntity<?> addUser(@RequestBody UserAddParam userAddParam) {
        log.info("新添加一个用户:{}", userAddParam);
        // TODO 后面这些校验放用注释校验
        if (StringUtils.isBlank(userAddParam.getUserName())
                || StringUtils.isBlank(userAddParam.getEmail())
                || StringUtils.isBlank(userAddParam.getMobile())
                || StringUtils.isBlank(userAddParam.getPassword())) {
            log.error("缺少必要参数");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.PARAM_EXEC.getMsg(), ResultStatus.PARAM_EXEC.getCode()), HttpStatus.OK);
        }

        return new ResponseEntity<>(BaseResult.handlerSuccess("新增用户成功", userService.addUser(userAddParam)), HttpStatus.OK);
    }

    /**
     * 根据id删除一个用户
     * @param id
     * @return
     */
    @DeleteMapping("/api/private/v1/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        log.info("即将删除的用户的id:{}", id);
        if (id < 0) {
            log.error("缺少必要参数");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.PARAM_EXEC.getMsg(), ResultStatus.PARAM_EXEC.getCode()), HttpStatus.OK);
        }

        return new ResponseEntity<>(BaseResult.handlerSuccess("删除用户成功", userService.deleteUserById(id)), HttpStatus.OK);
    }

    /**
     * 根据用户id修改用户信息
     * @return
     */
    @PutMapping("/api/private/v1/users/{id}")
    public ResponseEntity<?> editUserInfoById(@PathVariable Integer id, @RequestBody UserVO userVO) {
        log.info("根据id修改用户信息,id:{}", id);

        userVO.setId(id);
        return new ResponseEntity<>(BaseResult.handlerSuccess("修改用户成功", userService.editUserInfoById(userVO)), HttpStatus.OK);
    }

    /**
     * 根据用户id查找该用户信息
     * @param id
     * @return
     */
    @GetMapping("/api/private/v1/users/{id}")
    public ResponseEntity<?> selectUserById(@PathVariable Integer id) {
        log.info("根据id查找用户信息,id:{}", id);

        return new ResponseEntity<>(BaseResult.handlerSuccess("查找用户成功", userService.selectUserById(id)), HttpStatus.OK);
    }
}
