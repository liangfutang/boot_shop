package com.zjut.shop.controller;

import com.zjut.shop.query.ApplicationBizParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.ApplicationBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ApplicationBizController {

    @Autowired
    private ApplicationBizService applicationBizService;

    /**
     * 按需查询应用下面的业务列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/api/private/v1/application")
    public ResponseEntity<?> selectList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        ApplicationBizParam applicationBizParam = new ApplicationBizParam();
        applicationBizParam.setPageNum(pageNum - 1);
        applicationBizParam.setPageSize(pageSize);

        log.info("查询应用下面业务的参数:{}", applicationBizParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询应用业务列表成功", applicationBizService.selectApplicationBizList(applicationBizParam)), HttpStatus.OK);
    }

}
