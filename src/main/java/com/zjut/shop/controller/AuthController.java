package com.zjut.shop.controller;

import com.zjut.shop.enums.OperateEnum;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 根据类型查出权限列表
     * @param type
     * @return
     */
    @GetMapping("/api/private/v1/right/{type}")
    public ResponseEntity<?> selectRightList(@PathVariable String type) {
        log.info("查询权限列表的类型type:{}", type);

        // 传入的操作类型不满足要求
        // TODO 后面该校验放到注解中校验
        if (!OperateEnum.contains(type)) {
            log.error("操作类型异常");
            return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.OPERATE_TYPE_EXEC.getMsg(), ResultStatus.OPERATE_TYPE_EXEC.getCode()), HttpStatus.OK);
        }

        return new ResponseEntity<>(BaseResult.handlerSuccess("查询权限列表成功", authService.selectRightList(type)), HttpStatus.OK);
    }
}
