package com.zjut.shop.controller;

import com.zjut.shop.query.ApplicationParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 查询用户列表数据
     * @param pageNum，前端默认从1开始，后端默认从0开始
     * @param pageSize
     * @return
     */
    @GetMapping("/api/private/v1/application")
    public ResponseEntity<?> selectApplicationList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize
            , @RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "") String applicationName
            , @RequestParam(defaultValue = "") String principal, @RequestParam(defaultValue = "") String status) {
        log.info("查询短信应用列表参数,pageNum:{},pageSize:{}", pageNum, pageSize);
        ApplicationParam applicationParam = new ApplicationParam();
        applicationParam.setPageNum(pageNum-1);
        applicationParam.setPageSize(pageSize);
        // 模糊条件添加
        applicationParam.setId(id);
        applicationParam.setApplicationName(applicationName);
        applicationParam.setPrincipal(principal);
        applicationParam.setStatus(status);

        return new ResponseEntity<>(BaseResult.handlerSuccess("查询应用列表成功", applicationService.selectApplicationList(applicationParam)), HttpStatus.OK);
    }

    /**
     * 根据id修改应用的状态值
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/api/private/v1/application/{id}/{status}")
    public ResponseEntity<?> changeStatusById(@PathVariable String id, @PathVariable String status) {
        log.info("修改应用的id:{},status:{}", id, status);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id修改应用状态成功", applicationService.changeStatusById(id, status)), HttpStatus.OK);
    }

    /**
     * 根据id删除应用
     * @param id
     * @return
     */
    @DeleteMapping("/api/private/v1/application/{id}")
    public ResponseEntity<?> deleteAppById(@PathVariable String id) {
        log.info("删除应用的id:{}", id);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id删除应用成功", applicationService.deleteAppById(id)), HttpStatus.OK);
    }

    /**
     * 根据id修改应用信息
     * @param applicationParam
     * @return
     */
    @PutMapping("/api/private/v1/application")
    public ResponseEntity<?> updateApp(@RequestBody ApplicationParam applicationParam) {
        log.info("即将修改应用的参数:{}", applicationParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id修改应用成功", applicationService.updateApp(applicationParam)), HttpStatus.OK);
    }

    /**
     * 根据id获取应用
     * @param id
     * @return
     */
    @GetMapping("/api/private/v1/application/{id}")
    public ResponseEntity<?> selectAppById(@PathVariable String id) {
        log.info("删除应用的id:{}", id);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id删除应用成功", applicationService.selectAppById(id)), HttpStatus.OK);
    }
}
